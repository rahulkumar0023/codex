package com.example.csvbatch.config;

import com.example.csvbatch.model.CsvInvoiceDto;
import com.example.csvbatch.processor.InvoiceItemProcessor;
import com.example.csvbatch.writer.PerInvoiceXmlWriter;
import com.example.peppol.batch.util.CsvHeaderReader;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.annotation.PostConstruct;
import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final CsvHeaderReader csvHeaderReader;
    private final InvoiceItemProcessor processor;
    private final PerInvoiceXmlWriter writer;

    @Value("${batch.input.file:input/invoices.csv}")
    private String inputFile;

    @Value("${batch.chunk.size:5}")
    private int chunkSize;

    private String[] fieldNames;

    public BatchConfig(CsvHeaderReader csvHeaderReader,
                       InvoiceItemProcessor processor,
                       PerInvoiceXmlWriter writer) {
        this.csvHeaderReader = csvHeaderReader;
        this.processor = processor;
        this.writer = writer;
    }

    @PostConstruct
    public void init() throws IOException {
        this.fieldNames = csvHeaderReader.readHeaders(inputFile);
    }

    @Bean
    public FlatFileItemReader<CsvInvoiceDto> reader() {
        return new FlatFileItemReaderBuilder<CsvInvoiceDto>()
                .name("csvInvoiceReader")
                .resource(new FileSystemResource(inputFile))
                .linesToSkip(1)
                .delimited().delimiter(";")
                .names(fieldNames)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(CsvInvoiceDto.class);
                }})
                .build();
    }

    @Bean
    public Step csvStep(JobRepository jobRepository, PlatformTransactionManager txManager,
                        ItemReader<CsvInvoiceDto> reader) {
        return new StepBuilder("csvStep", jobRepository)
                .<CsvInvoiceDto, InvoiceType>chunk(chunkSize, txManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job invoiceJob(JobRepository jobRepository, Step csvStep) {
        return new JobBuilder("invoiceJob", jobRepository)
                .start(csvStep)
                .build();
    }
}
