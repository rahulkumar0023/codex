package com.example.peppol.batch;

import java.nio.file.Path;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job peppolInvoiceJob(JobBuilderFactory jobs, Step extractXmlStep) {
        return jobs.get("peppolInvoiceJob")
                .incrementer(new RunIdIncrementer())
                .flow(extractXmlStep)
                .end()
                .build();
    }

    @Bean
    public Step extractXmlStep(StepBuilderFactory steps, ItemReader<InvoiceDocument> reader, ItemWriter<InvoiceDocument> writer) {
        return steps.get("extractXmlStep")
                .<InvoiceDocument, InvoiceDocument>chunk(1)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<InvoiceDocument> reader() {
        return new InvoiceXmlFileReader(Path.of("input"));
    }

    @Bean
    public ItemWriter<InvoiceDocument> writer() {
        return new InvoiceXmlWriter(Path.of("output"));
    }
}
