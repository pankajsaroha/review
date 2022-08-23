package com.review.config;

import com.review.model.Movie;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private CustomItemReader itemReader;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job startJob(final List<Movie> movies) throws Exception {
        return jobBuilderFactory
                .get("batch-insert-movies")
                .incrementer(new RunIdIncrementer())
                .start(step(movies))
                .build();
    }

    @Bean
    public Step step (final List<Movie> movies) throws Exception {
        return stepBuilderFactory
                .get("step")
                .<Movie, Movie>chunk(5)
                .reader(itemReader)
                .processor(processor())
                .writer(writer(movies))
                .build();
    }


    @Bean
    public ItemProcessor<Movie, Movie> processor () {
        return null;
    }

    @Bean
    public JdbcBatchItemWriter<Movie> writer(final List<Movie> movies) throws Exception {
        JdbcBatchItemWriter<Movie> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(this.dataSource);
        itemWriter.setSql("insert into movies (id, genre, name, release) values (:id, :genre, :name, :release)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        itemWriter.afterPropertiesSet();
        itemWriter.write(movies);
        return itemWriter;
    }
}
