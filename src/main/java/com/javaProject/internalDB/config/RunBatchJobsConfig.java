package com.javaProject.internalDB.config;
 
import javax.sql.DataSource;

import org.h2.server.web.WebServlet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.javaProject.internalDB.model.Person;
 
@Configuration
@EnableBatchProcessing
public class RunBatchJobsConfig {
	
	@Autowired
    JobListenersBefAfter interceptingJob;
     
    @Autowired
    private JobBuilderFactory jobBuildFact;
 
    @Autowired
    private StepBuilderFactory stepBuildFact;
 
    @Value("classPath:/input/ms3Interview_orig.csv")
    private Resource inputFileCsv;
    
    
 
    @Bean
    public Job readCSVFileJob() {
        return jobBuildFact
                .get("readCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .start(step()).listener(interceptingJob)
                .build();
    }
 
    @Bean
    public Step step() {
        return stepBuildFact
                .get("step")
                .<Person, Person>chunk(20)
                .reader(flatFilereader())
                .processor(processor())
                .writer(writer())
                .build();
    }
     
    @Bean
    public ItemProcessor<Person, Person> processor() {
        return new ItemProcessorModelClass();
    }
     
    @Bean
    public FlatFileItemReader<Person> flatFilereader() {
        FlatFileItemReader<Person> itemReader = new FlatFileItemReader<Person>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputFileCsv);
        return itemReader;
    }
 
    @Bean
    public LineMapper<Person> lineMapper() {
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<Person>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] {"firstName", "lastName","emailId", "gender", "image", "cardType", "price","flag1", "flag2", "city"});
        lineTokenizer.setIncludedFields(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 });
                
        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<Person>();
        fieldSetMapper.setTargetType(Person.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
 
    @Bean
    public JdbcBatchItemWriter<Person> writer() {
        JdbcBatchItemWriter<Person> itemWriter = new JdbcBatchItemWriter<Person>();
        itemWriter.setDataSource(dataSource());
        itemWriter.setSql("INSERT INTO PERSON (A,B,C,D,E,F,G,H,I,J) VALUES (:firstName, :lastName, :emailId, :gender, :image, :cardType, :price, :flag1, :flag2, :city)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        return itemWriter;
    }
    
    @Bean
    public ServletRegistrationBean<WebServlet> h2servletRegistration() {
        ServletRegistrationBean<WebServlet> registration = new ServletRegistrationBean<WebServlet>(new WebServlet());
        registration.addUrlMappings("/console/*");
        registration.addInitParameter("webAllowOthers", "true");
        return registration;
    }
     
    @Bean
    public DataSource dataSource(){
        EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
        return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .addScript("classpath:person.sql")
                .setType(EmbeddedDatabaseType.H2) 
                .build();
    }
}