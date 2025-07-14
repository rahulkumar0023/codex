package com.example.peppol.batch;

import java.nio.file.Path;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.Resource;

import com.example.peppol.batch.tasklet.FetchDecryptUnzipTasklet;
import com.example.peppol.batch.tasklet.PackageAndUploadTasklet;
import com.example.peppol.batch.tasklet.CleanupTasklet;
import com.example.peppol.batch.XmlInvoiceReader;
import com.example.peppol.batch.XmlCreditNoteReader;
import com.example.peppol.batch.XmlInvoiceWriter;
import com.example.peppol.batch.XmlCreditNoteWriter;
import com.example.peppol.batch.csv.CsvInvoiceReader;
import com.example.peppol.batch.csv.CsvInvoiceProcessor;
import com.example.peppol.batch.csv.CsvInvoiceRecord;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import network.oxalis.peppol.ubl2.jaxb.CreditNoteType;

@Configuration
@EnableBatchProcessing

public class BatchConfig {


    /**
     * Job showcasing a simple end-to-end flow including fetching archives,
     * parsing invoices and credit notes and packaging the results.
     */
    @Bean
    public Job processingJob(JobBuilderFactory jobs,
                             Step fetchStep,
                             Step xmlInvoiceStep,
                             Step xmlCreditNoteStep,
                             Step packageAndUploadStep,
                             Step cleanupStep) {
        return jobs.get("processingJob")
                .incrementer(new RunIdIncrementer())
                .start(fetchStep)
                .next(xmlInvoiceStep)
                .next(xmlCreditNoteStep)
                .next(packageAndUploadStep)
                .next(cleanupStep)
                .build();
    }

    /**
     * Variant job that reads invoices from CSV files and writes them to XML.
     */
    @Bean
    public Job csvProcessingJob(JobBuilderFactory jobs,
                                Step fetchStep,
                                Step csvInvoiceStep,
                                Step packageAndUploadStep,
                                Step cleanupStep) {
        return jobs.get("csvProcessingJob")
                .incrementer(new RunIdIncrementer())
                .start(fetchStep)
                .next(csvInvoiceStep)
                .next(packageAndUploadStep)
                .next(cleanupStep)
                .build();
    }



    // ---------------------------------------------------------------------
    // Extended job step definitions
    // ---------------------------------------------------------------------

    @Bean
    public Step fetchStep(StepBuilderFactory steps) {
        Tasklet tasklet = new FetchDecryptUnzipTasklet(Path.of("input"), Path.of("tmp/unzipped"));
        return steps.get("fetchStep").tasklet(tasklet).build();
    }

    @Bean
    public MultiResourceItemReader<InvoiceType> xmlInvoiceReader() throws IOException {
        MultiResourceItemReader<InvoiceType> reader = new MultiResourceItemReader<>();
        Resource[] allXml = new PathMatchingResourcePatternResolver()
                .getResources("file:" + Path.of("tmp/unzipped/xml").toAbsolutePath() + "/*.xml");
        Resource[] invoiceResources = Arrays.stream(allXml)
                .filter(r -> r.getFilename() != null
                        && r.getFilename().toLowerCase().contains("inv"))
                .toArray(Resource[]::new);
        reader.setResources(invoiceResources);
        reader.setDelegate(new XmlInvoiceReader());
        return reader;
    }

    @Bean
    public Step xmlInvoiceStep(StepBuilderFactory steps, MultiResourceItemReader<InvoiceType> xmlInvoiceReader) {
        XmlInvoiceWriter writer = new XmlInvoiceWriter(Path.of("tmp/processed/xml"));
        return steps.get("xmlInvoiceStep")
                .<InvoiceType, InvoiceType>chunk(5)
                .reader(xmlInvoiceReader)
                .writer(writer)
                .build();
    }

    @Bean
    public MultiResourceItemReader<CreditNoteType> xmlCreditNoteReader() throws IOException {
        MultiResourceItemReader<CreditNoteType> reader = new MultiResourceItemReader<>();
        reader.setResources(new PathMatchingResourcePatternResolver()
                .getResources("file:" + Path.of("tmp/unzipped/xml").toAbsolutePath() + "/*CN*.xml"));
        reader.setDelegate(new XmlCreditNoteReader());
        return reader;
    }

    @Bean
    public Step xmlCreditNoteStep(StepBuilderFactory steps, MultiResourceItemReader<CreditNoteType> xmlCreditNoteReader) {
        XmlCreditNoteWriter writer = new XmlCreditNoteWriter(Path.of("tmp/processed/xml"));
        return steps.get("xmlCreditNoteStep")
                .<CreditNoteType, CreditNoteType>chunk(5)
                .reader(xmlCreditNoteReader)
                .writer(writer)
                .build();
    }

    @Bean
    public MultiResourceItemReader<CsvInvoiceRecord> csvInvoiceReader() throws IOException {
        MultiResourceItemReader<CsvInvoiceRecord> reader = new MultiResourceItemReader<>();
        reader.setResources(new PathMatchingResourcePatternResolver()
                .getResources("file:" + Path.of("tmp/unzipped/csv").toAbsolutePath() + "/*.csv"));
        reader.setDelegate(new CsvInvoiceReader());
        return reader;
    }

    @Bean
    public Step csvInvoiceStep(StepBuilderFactory steps,
                               MultiResourceItemReader<CsvInvoiceRecord> csvInvoiceReader,
                               CsvInvoiceProcessor processor) {
        XmlInvoiceWriter writer = new XmlInvoiceWriter(Path.of("tmp/processed/xml"));
        return steps.get("csvInvoiceStep")
                .<CsvInvoiceRecord, InvoiceType>chunk(5)
                .reader(csvInvoiceReader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step packageAndUploadStep(StepBuilderFactory steps) {
        Tasklet tasklet = new PackageAndUploadTasklet(Path.of("tmp/processed"), Path.of("upload"));
        return steps.get("packageAndUploadStep").tasklet(tasklet).build();
    }

    @Bean
    public Step cleanupStep(StepBuilderFactory steps,
                            @Value("${cleanup.dirs}") String[] cleanupDirs) {
        Path[] paths = Arrays.stream(cleanupDirs)
                .map(Path::of)
                .toArray(Path[]::new);
        Tasklet tasklet = new CleanupTasklet(paths);
        return steps.get("cleanupStep").tasklet(tasklet).build();
    }

}
