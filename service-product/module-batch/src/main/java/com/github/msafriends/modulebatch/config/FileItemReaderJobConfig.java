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
import com.github.msafriends.modulebatch.csv.ElevenStreetCSVImpl;
import com.github.msafriends.modulebatch.csv.ExtractedElevenStreetCSV;
import com.github.msafriends.modulebatch.listener.JobCompletionNotificationListener;
import com.github.msafriends.modulebatch.processor.ElevenStreetItemProcessor;
import com.github.msafriends.modulebatch.processor.ElevenStreetNewItemProcessor;

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
    private final ElevenStreetItemProcessor processor;
    private final ElevenStreetNewItemProcessor newItemProcessor;
    private Environment environment;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final JobCompletionNotificationListener listener;

    @Bean
    public FlatFileItemReader<ElevenStreetCSVImpl> reader(){
        FlatFileItemReader flatFileItemReader = new FlatFileItemReaderBuilder<>()
            .name(ITEM_READER_NAME)
            .resource(new ClassPathResource(ELEVEN_STREET_CSV_RESOURCE_URL))
            .delimited()
            .names("id", "ProductCode", "ProductName", "ProductPrice", "ProductImage", "ProductImage100",
                "ProductImage110", "ProductImage120", "ProductImage130", "ProductImage140", "ProductImage150",
                "ProductImage170", "ProductImage200", "ProductImage250", "ProductImage270", "ProductImage300",
                "Text1", "Text2", "SellerNick", "Seller", "SellerGrd", "Rating", "DetailPageUrl",
                "SalePrice", "Delivery", "ReviewCount", "BuySatisfy", "MinorYn", "Benefit")
            .fieldSetMapper(new BeanWrapperFieldSetMapper<>(){
                {setTargetType(ElevenStreetCSVImpl.class);}
            })
            .build();
        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<ExtractedElevenStreetCSV> stepWriterProductTable(){
        QueryStrategyParams queryStrategyParams = getProductQueryStrategyParams();
        String sql = new ProfileBasedQueryStrategyProvider(environment, queryStrategyParams).getQuery();
        return new JdbcBatchItemWriterBuilder<ExtractedElevenStreetCSV>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql(sql)
            .dataSource(dataSource)
            .build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<ExtractedElevenStreetCSV> stepWriterProductImageTable(){
        QueryStrategyParams queryStrategyParams = getProductImageQueryStrategyParams();
        String sql = new ProfileBasedQueryStrategyProvider(environment, queryStrategyParams).getQuery();
        return new JdbcBatchItemWriterBuilder<ExtractedElevenStreetCSV>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql(sql)
            .dataSource(dataSource)
            .build();
    }

    @Bean
    @StepScope
    public CompositeItemWriter<ExtractedElevenStreetCSV> compositeItemWriter(
        @Qualifier("stepWriterProductTable") JdbcBatchItemWriter<ExtractedElevenStreetCSV> stepWriterProductTable,
        @Qualifier("stepWriterProductImageTable") JdbcBatchItemWriter<ExtractedElevenStreetCSV> stepWriterProductImageTable
    ){
        CompositeItemWriter<ExtractedElevenStreetCSV> writer  = new CompositeItemWriter<>();
        writer.setDelegates(List.of(stepWriterProductTable, stepWriterProductImageTable));
        return writer;
    }

    private QueryStrategyParams getProductQueryStrategyParams(){
        return QueryStrategyParams.builder()
            .tableName("products")
            .columnNames(new String[]{"code", "name", "price_value", "sale_price_value", "quantity", "delivery", "discount", "mileage", "age_limit", "seller_id"})
            //Value는 CSV 기준
            .columnValues(new String[]{":code", ":name", ":priceValue", ":salePriceValue", ":quantity", ":delivery", ":discount", ":mileage", ":ageLimit", ":sellerId"})
            .build();
    }

    @Bean
    public Step stepElevenStreetProductAndProductImageDataMigration(){
        return new StepBuilder("step - loading elevenStreet seller data into database", jobRepository)
            .<ElevenStreetCSVImpl, ExtractedElevenStreetCSV>chunk(CHUNK_SIZE, transactionManager)
            .reader(reader())
            .processor(newItemProcessor)
            .writer(compositeItemWriter(stepWriterProductTable(), stepWriterProductImageTable()))
            .build();
    }

    @Bean
    @Qualifier("elevenStreetProductAndProductImageDataMigration")
    public Job elevenStreetSellerDataMigrationJob(){
        return new JobBuilder("elevenStreetProductAndProductImageDataMigration", jobRepository)
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .start(stepElevenStreetProductAndProductImageDataMigration())
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
