package com.github.msafriends.modulebatch.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.github.msafriends.modulebatch.config.strategy.ProfileBasedQueryStrategyProvider;
import com.github.msafriends.modulebatch.config.strategy.QueryStrategyParams;
import com.github.msafriends.modulebatch.csv.ElevenStreetCSV;
import com.github.msafriends.modulebatch.csv.ProductElevenStreetCSV;
import com.github.msafriends.modulebatch.csv.ProductImageElevenStreetCSV;
import com.github.msafriends.modulebatch.listener.JobCompletionNotificationListener;
import com.github.msafriends.modulebatch.processor.ElevenStreetProductImageItemProcessor;
import com.github.msafriends.modulebatch.processor.ElevenStreetProductItemProcessor;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class FileItemReaderJobConfig {
    private static final int CHUNK_SIZE = 1000;
    private static final String ITEM_READER_NAME = "elevenStreetItemReader";
    private static final String ELEVEN_STREET_CSV_FILE_NAME = "productDataWithSellerId.csv";
    private static final String ELEVEN_STREET_CSV_RESOURCE_URL = "/" + ELEVEN_STREET_CSV_FILE_NAME;

    private final DataSource dataSource;
    private final ElevenStreetProductItemProcessor productProcessor;
    private final ElevenStreetProductImageItemProcessor productImageProcessor;
    private final Environment environment;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final JobCompletionNotificationListener listener;

    @Bean
    public FlatFileItemReader<ElevenStreetCSV> reader(){
        FlatFileItemReader flatFileItemReader = new FlatFileItemReaderBuilder<>()
            .name(ITEM_READER_NAME)
            .resource(new ClassPathResource(ELEVEN_STREET_CSV_RESOURCE_URL))
            .delimited()
            .names("id", "ProductCode", "ProductName", "ProductPrice", "ProductImage", "ProductImage100",
                "ProductImage110", "ProductImage120", "ProductImage130", "ProductImage140", "ProductImage150",
                "ProductImage170", "ProductImage200", "ProductImage250", "ProductImage270", "ProductImage300",
                "Text1", "Text2", "SellerNick", "Seller", "SellerGrd", "Rating", "DetailPageUrl",
                "SalePrice", "Delivery", "ReviewCount", "BuySatisfy", "MinorYn", "Benefit", "sellerId")
            .fieldSetMapper(new BeanWrapperFieldSetMapper<>(){
                {setTargetType(ElevenStreetCSV.class);}
            })
            .build();
        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<ProductElevenStreetCSV> stepWriterProductTable(){
        QueryStrategyParams queryStrategyParams = getProductQueryStrategyParams();
        String sql = new ProfileBasedQueryStrategyProvider(environment, queryStrategyParams).getQuery();
        return new JdbcBatchItemWriterBuilder<ProductElevenStreetCSV>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql(sql)
            .dataSource(dataSource)
            .build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<ProductImageElevenStreetCSV> stepWriterProductImageTable(){
        QueryStrategyParams queryStrategyParams = getProductImageQueryStrategyParams();
        String sql = new ProfileBasedQueryStrategyProvider(environment, queryStrategyParams).getQuery();
        return new JdbcBatchItemWriterBuilder<ProductImageElevenStreetCSV>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql(sql)
            .dataSource(dataSource)
            .build();
    }

    @Bean
    @StepScope
    public CompositeItemWriter<ProductElevenStreetCSV> compositeProductWriter(
        @Qualifier("stepWriterProductTable") JdbcBatchItemWriter<ProductElevenStreetCSV> stepWriterProductTable
    ){
        CompositeItemWriter<ProductElevenStreetCSV> writer  = new CompositeItemWriter<>();
        writer.setDelegates(List.of(stepWriterProductTable));
        return writer;
    }

    @Bean
    @StepScope
    public CompositeItemWriter<ProductImageElevenStreetCSV> compositeProductImageWriter(
        @Qualifier("stepWriterProductImageTable") JdbcBatchItemWriter<ProductImageElevenStreetCSV> stepWriterProductImageTable
    ){
       CompositeItemWriter<ProductImageElevenStreetCSV> writer = new CompositeItemWriter<>();
       writer.setDelegates(List.of(stepWriterProductImageTable));
       return writer;
    }

    private QueryStrategyParams getProductQueryStrategyParams(){
        return QueryStrategyParams.builder()
            .tableName("products")
            .columnNames(new String[]{"code", "name", "price_value", "sale_price_value", "quantity", "delivery", "buy_satisfy", "discount", "mileage", "age_limit", "seller_id"})
            .columnValues(new String[]{":code", ":name", ":priceValue", ":salePriceValue", ":quantity", ":delivery", ":buySatisfy", ":discount", ":mileage", ":ageLimit", ":sellerId"})
            .uniqueColumnName("code")
            .uniqueColumnValue(":code")
            .build();
    }

    @Bean
    public Step stepElevenStreetProductDataMigration(){
        return new StepBuilder("step - loading elevenStreet product data into database", jobRepository)
            .<ElevenStreetCSV, ProductElevenStreetCSV>chunk(CHUNK_SIZE, transactionManager)
            .reader(reader())
            .processor(productProcessor)
            .writer(compositeProductWriter(stepWriterProductTable()))
            .faultTolerant()
            .skip(FlatFileParseException.class)
            .skipLimit(10)
            .build();
    }

    @Bean
    public Step stepElevenStreetProductImageDataMigration(){
        return new StepBuilder("step - loading elevenStreet productImage data into database", jobRepository)
            .<ElevenStreetCSV, ProductImageElevenStreetCSV>chunk(CHUNK_SIZE, transactionManager)
            .reader(reader())
            .processor(productImageProcessor)
            .writer(compositeProductImageWriter(stepWriterProductImageTable()))
            .faultTolerant()
            .skip(FlatFileParseException.class)
            .skipLimit(10)
            .build();
    }

    @Bean
    @Qualifier("elevenStreetProductDataMigration")
    public Job elevenStreetProductDataMigrationJob(){
        return new JobBuilder("elevenStreetProductDataMigration", jobRepository)
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .start(stepElevenStreetProductDataMigration())
            .build();
    }

    @Bean
    @Qualifier("elevenStreetProductImageDataMigration")
    public Job elevenStreetProductImageDataMigrationJob(){
        return new JobBuilder("elevenStreetProductImageDataMigration", jobRepository)
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .start(stepElevenStreetProductImageDataMigration())
            .build();
    }

    private QueryStrategyParams getProductImageQueryStrategyParams(){
        return QueryStrategyParams.builder()
            .tableName("product_images")
            .columnNames(new String[]{
                "product_id",
                "base", "size_100",
                "size_110", "size_120",
                "size_130", "size_140",
                "size_150", "size_170",
                "size_200", "size_250",
                "size_270", "size_300"})
            .columnValues(new String[]{
                ":productId",
                ":base", ":size100",
                ":size110", ":size120",
                ":size130", ":size140",
                ":size150", ":size170",
                ":size200", ":size250",
                ":size270", ":size300"
            })
            .build();
    }
}
