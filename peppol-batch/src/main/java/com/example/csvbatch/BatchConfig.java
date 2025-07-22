package com.example.csvbatch;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.nio.file.Path;

import static com.example.csvbatch.CsvFieldNames.INVOICE_FIELDS;

@Configuration
@EnableBatchProcessing
public class BatchConfig {


    private final InvoiceProcessor processor;
    public BatchConfig(InvoiceProcessor processor) {
        this.processor = processor;
    }

    @Bean
    public MultiResourceItemReader<CsvInvoiceDto> reader() {
        MultiResourceItemReader<CsvInvoiceDto> reader = new MultiResourceItemReader<>();
        reader.setDelegate(new CsvInvoiceReader());

        FileSystemResource[] inputFiles = new FileSystemResource[] {
                new FileSystemResource("input/invoices.csv")
                // Add more if needed
        };
        reader.setResources(inputFiles);

        return reader;
    }


    @Bean
    public XmlInvoiceWriter xmlInvoiceWriter() {
        return new XmlInvoiceWriter(Path.of("output")); // or Path.of("target/xml-out")
    }

    @Bean
    public Step csvToXmlStep(JobRepository repo, PlatformTransactionManager tx, XmlInvoiceWriter writer) {
        return new StepBuilder("csvToXmlStep", repo)
                .<CsvInvoiceDto, InvoiceType>chunk(10, tx)
                .reader(reader())
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job invoiceJob(JobRepository repo, Step step) {
        return new JobBuilder("invoiceJob", repo)
                .start(step)
                .build();
    }
}
