package com.github.msafriends.modulebatch.config;

import com.github.msafriends.modulebatch.config.strategy.ProfileBasedQueryStrategyProvider;
import com.github.msafriends.modulebatch.config.strategy.QueryStrategyParams;
import com.github.msafriends.modulebatch.csv.ElevenStreetCSV;
import com.github.msafriends.modulebatch.listener.JobCompletionNotificationListener;
import com.github.msafriends.modulebatch.processor.ElevenStreetItemProcessor;
import com.github.msafriends.modulebatch.processor.ElevenStreetNewItemProcessor;
import lombok.RequiredArgsConstructor;
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
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
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

import javax.sql.DataSource;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class FileItemReaderJobConfig {

    private static final int CHUNK_SIZE = 1000;
    private static final String ITEM_READER_NAME = "elevenStreetItemsReader";
    private static final String ELEVEN_STREET_CSV_FILE_NAME = "out.csv";
    private static final String ELEVEN_STREET_CSV_RESOURCE_URL = "/" + ELEVEN_STREET_CSV_FILE_NAME;

    private final DataSource dataSource;
    private final ElevenStreetItemProcessor processor;
    private final ElevenStreetNewItemProcessor newItemProcessor;
    private final Environment environment;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final JobCompletionNotificationListener listener;


    @Bean
    public FlatFileItemReader<ElevenStreetCSV> reader() {
        FlatFileItemReader flatFileItemReader = new FlatFileItemReaderBuilder<>()
                .name(ITEM_READER_NAME)
                .resource(new ClassPathResource(ELEVEN_STREET_CSV_RESOURCE_URL))
                .delimited()
                .names("id", "ProductCode", "ProductName", "ProductPrice", "ProductImage", "ProductImage100",
                        "ProductImage110", "ProductImage120", "ProductImage130", "ProductImage140", "ProductImage150",
                        "ProductImage170", "ProductImage200", "ProductImage250", "ProductImage270", "ProductImage300",
                        "Text1", "Text2", "SellerNick", "Seller", "SellerGrd", "Rating", "DetailPageUrl",
                        "SalePrice", "Delivery", "ReviewCount", "BuySatisfy", "MinorYn", "Benefit")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {
                    {setTargetType(ElevenStreetCSV.class);}
                })
                .build();
        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }

    @Bean
    public FlatFileItemReader<ElevenStreetCSV> readerCopyVersionWithSeller() {
        String outCSVName = "out_" + ELEVEN_STREET_CSV_FILE_NAME;
        CSVCopier.copy(ELEVEN_STREET_CSV_FILE_NAME, outCSVName, "sellerId");

        FlatFileItemReader flatFileItemReader = new FlatFileItemReaderBuilder<>()
                .name(ITEM_READER_NAME)
                .resource(new FileSystemResource("out_out.csv"))
                .delimited()
                .names("id", "ProductCode", "ProductName", "ProductPrice", "ProductImage", "ProductImage100",
                        "ProductImage110", "ProductImage120", "ProductImage130", "ProductImage140", "ProductImage150",
                        "ProductImage170", "ProductImage200", "ProductImage250", "ProductImage270", "ProductImage300",
                        "Text1", "Text2", "SellerNick", "Seller", "SellerGrd", "Rating", "DetailPageUrl",
                        "SalePrice", "Delivery", "ReviewCount", "BuySatisfy", "MinorYn", "Benefit", "sellerId")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {
                    {setTargetType(ElevenStreetCSV.class);}
                })
                .build();
        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<ElevenStreetCSV> stepWriterSellerTable() {
        QueryStrategyParams queryStrategyParams = getSellerQueryStrategyParams();
        String sql = new ProfileBasedQueryStrategyProvider(environment, queryStrategyParams).getQuery();

        return new JdbcBatchItemWriterBuilder<ElevenStreetCSV>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(sql)
                .dataSource(dataSource)
                .build();
    }

    @SneakyThrows
    @Bean
    @StepScope
    public JdbcPagingItemReader<ElevenStreetCSV> stepWriterCsvWithSellerTable() {
        SqlPagingQueryProviderFactoryBean pagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        pagingQueryProviderFactoryBean.setDataSource(dataSource);
        pagingQueryProviderFactoryBean.setSelectClause("SELECT SELLER_ID");
        pagingQueryProviderFactoryBean.setFromClause("FROM SELLERS");
        pagingQueryProviderFactoryBean.setSortKey("SELLER_ID");

        return new JdbcPagingItemReaderBuilder<ElevenStreetCSV>()
                .name("myDataReader")
                .dataSource(dataSource)
                .queryProvider(Objects.requireNonNull(pagingQueryProviderFactoryBean.getObject()))
                .pageSize(1000)
                .rowMapper(new BeanPropertyRowMapper<>(ElevenStreetCSV.class))
                .build();
    }

    @Bean
    @StepScope
    public CompositeItemWriter<ElevenStreetCSV> compositeItemWriter(
            @Qualifier("stepWriterSellerTable") JdbcBatchItemWriter<ElevenStreetCSV> stepWriterSellerTable
    ) {
        CompositeItemWriter<ElevenStreetCSV> writer = new CompositeItemWriter<>();
        writer.setDelegates(List.of(stepWriterSellerTable));
        return writer;
    }

    @Bean
    @Qualifier("elevenStreetSellerDataMigration")
    public Job elevenStreetSellerDataMigrationJob() {
        return new JobBuilder("elevenStreetSellerDataMigration", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(stepElevenStreetSellerDataMigration())
                .build();
    }

    @Bean
    @Qualifier("CsvWithElevenStreetSellerData")
    public Job exportCsvWithElevenStreetSellerDataJob() {
        return new JobBuilder("CsvWithElevenStreetSellerData", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(stepWriteCSVWithElevenStreetSellerData())
                .build();
    }

    @Bean
    public Step stepElevenStreetSellerDataMigration() {
        return new StepBuilder("step - loading elevenStreet seller data into database", jobRepository)
                .<ElevenStreetCSV, ElevenStreetCSV>chunk(CHUNK_SIZE, transactionManager)
                .reader(reader())
                .processor(processor)
                .writer(compositeItemWriter(stepWriterSellerTable()))
                .build();
    }

    @Bean
    public Step stepWriteCSVWithElevenStreetSellerData() {
        return new StepBuilder("step - loading elevenStreet seller data into csv", jobRepository)
                .<ElevenStreetCSV, ElevenStreetCSV>chunk(CHUNK_SIZE, transactionManager)
                .reader(readerCopyVersionWithSeller())
                .processor(newItemProcessor)
                .writer(csvItemWriter())
                .build();
    }

    @Bean
    public ItemWriter<ElevenStreetCSV> csvItemWriter() {
        FlatFileItemWriter<ElevenStreetCSV> writer = new FlatFileItemWriter<>();

        writer.setResource(new FileSystemResource("output.csv"));

        writer.setHeaderCallback(writerItem -> writerItem.write("id,ProductCode,ProductName,ProductPrice,ProductImage,ProductImage100,"
                + "ProductImage110,ProductImage120,ProductImage130,ProductImage140,ProductImage150,"
                + "ProductImage170,ProductImage200,ProductImage250,ProductImage270,ProductImage300,"
                + "Text1,Text2,SellerNick,Seller,SellerGrd,Rating,DetailPageUrl,"
                + "SalePrice,Delivery,ReviewCount,BuySatisfy,MinorYn,Benefit,sellerId"));


        BeanWrapperFieldExtractor<ElevenStreetCSV> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] {"id", "ProductCode", "ProductName", "ProductPrice", "ProductImage", "ProductImage100",
                "ProductImage110", "ProductImage120", "ProductImage130", "ProductImage140", "ProductImage150",
                "ProductImage170", "ProductImage200", "ProductImage250", "ProductImage270", "ProductImage300",
                "Text1", "Text2", "SellerNick", "Seller", "SellerGrd", "Rating", "DetailPageUrl",
                "SalePrice", "Delivery", "ReviewCount", "BuySatisfy", "MinorYn", "Benefit", "sellerId"});
        DelimitedLineAggregator<ElevenStreetCSV> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setFieldExtractor(fieldExtractor);
        writer.setLineAggregator(lineAggregator);

        return writer;
    }


    private QueryStrategyParams getSellerQueryStrategyParams() {
        return QueryStrategyParams.builder()
                .tableName("SELLERS")
                .columnNames(new String[]{"nick_name", "user_name", "grade"})
                .columnValues(new String[]{":SellerNick", ":Seller", ":SellerGrd"})
                .uniqueColumnName("nick_name")
                .uniqueColumnValue(":SellerNick")
                .build();
    }
}
