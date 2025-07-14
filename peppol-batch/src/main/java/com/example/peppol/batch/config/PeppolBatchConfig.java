package com.example.peppol.batch.config;

import com.example.peppol.batch.csv.*;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class PeppolBatchConfig {

    @Value("${invoice.csv.file:/tmp/invoices/input.csv}")
    private String inputFile;

    @Bean
    public ResourceAwareItemReaderItemStream<CsvInvoiceRecord> csvInvoiceReader() {
        CsvInvoiceReader reader = new CsvInvoiceReader();
        reader.setResource(new FileSystemResource(inputFile));
        return reader;
    }

    @Bean
    public Job peppolJob(JobRepository jobRepository, Step peppolStep) {
        return new JobBuilder("peppolJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(peppolStep)
                .end()
                .build();
    }

    @Bean
    public Step peppolStep(JobRepository jobRepository,
                           ResourceAwareItemReaderItemStream<CsvInvoiceRecord> csvInvoiceReader,
                           ItemProcessor<CsvInvoiceRecord, InvoiceType> processor,
                           ItemWriter<InvoiceType> writer,
                           PlatformTransactionManager transactionManager) {
        return new StepBuilder("peppolStep", jobRepository)
                .<CsvInvoiceRecord, InvoiceType>chunk(10, transactionManager)
                .reader(csvInvoiceReader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
