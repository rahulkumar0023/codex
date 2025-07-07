package com.example.peppol.batch;

import java.nio.file.Path;
import java.io.IOException;

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

import com.example.peppol.batch.tasklet.InvoiceReadTasklet;
import com.example.peppol.batch.tasklet.InvoiceWriteTasklet;
import com.example.peppol.batch.tasklet.FetchDecryptUnzipTasklet;
import com.example.peppol.batch.tasklet.PackageAndUploadTasklet;
import com.example.peppol.batch.tasklet.CleanupTasklet;
import com.example.peppol.batch.InvoiceXmlWriter;
import com.example.peppol.batch.InvoiceDocument;
import com.example.peppol.batch.PdfInvoiceXmlReader;
import com.example.peppol.batch.XmlInvoiceReader;
import com.example.peppol.batch.XmlCreditNoteReader;
import com.example.peppol.batch.XmlInvoiceWriter;
import com.example.peppol.batch.XmlCreditNoteItemWriter;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import network.oxalis.peppol.ubl2.jaxb.CreditNoteType;

@Configuration
@EnableBatchProcessing

public class BatchConfig {

    @Bean
    public Job peppolInvoiceJob(JobBuilderFactory jobs, Step readStep, Step writeStep) {
        return jobs.get("peppolInvoiceJob")
                .incrementer(new RunIdIncrementer())
                .start(readStep)
                .next(writeStep)
                .build();
    }

    /**
     * Job showcasing a simple end-to-end flow including fetching archives,
     * parsing invoices and credit notes and packaging the results.
     */
    @Bean
    public Job processingJob(JobBuilderFactory jobs,
                             Step fetchStep,
                             Step xmlInvoiceStep,
                             Step xmlCreditNoteStep,
                             Step pdfInvoiceStep,
                             Step packageAndUploadStep,
                             Step cleanupStep) {
        return jobs.get("processingJob")
                .incrementer(new RunIdIncrementer())
                .start(fetchStep)
                .next(xmlInvoiceStep)
                .next(xmlCreditNoteStep)
                .next(pdfInvoiceStep)
                .next(packageAndUploadStep)
                .next(cleanupStep)
                .build();
    }

    @Bean
    public Step readStep(StepBuilderFactory steps) {
        Tasklet readTasklet = new InvoiceReadTasklet(Path.of("input"));
        return steps.get("readStep").tasklet(readTasklet).build();
    }

    @Bean
    public Step writeStep(StepBuilderFactory steps) {
        Tasklet writeTasklet = new InvoiceWriteTasklet(Path.of("output"));
        return steps.get("writeStep").tasklet(writeTasklet).build();
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
        reader.setResources(new PathMatchingResourcePatternResolver()
                .getResources("file:" + Path.of("tmp/unzipped").toAbsolutePath() + "/*INV*.xml"));
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
                .getResources("file:" + Path.of("tmp/unzipped").toAbsolutePath() + "/*CN*.xml"));
        reader.setDelegate(new XmlCreditNoteReader());
        return reader;
    }

    @Bean
    public Step xmlCreditNoteStep(StepBuilderFactory steps, MultiResourceItemReader<CreditNoteType> xmlCreditNoteReader) {
        XmlCreditNoteItemWriter writer = new XmlCreditNoteItemWriter(Path.of("tmp/processed/xml"));
        return steps.get("xmlCreditNoteStep")
                .<CreditNoteType, CreditNoteType>chunk(5)
                .reader(xmlCreditNoteReader)
                .writer(writer)
                .build();
    }

    @Bean
    public Step pdfInvoiceStep(StepBuilderFactory steps) {
        PdfInvoiceXmlReader reader = new PdfInvoiceXmlReader(Path.of("tmp/unzipped"));
        InvoiceXmlWriter writer = new InvoiceXmlWriter(Path.of("tmp/processed/pdf"));
        return steps.get("pdfInvoiceStep")
                .<InvoiceDocument, InvoiceDocument>chunk(5)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Step packageAndUploadStep(StepBuilderFactory steps) {
        Tasklet tasklet = new PackageAndUploadTasklet(Path.of("tmp/processed"), Path.of("upload"));
        return steps.get("packageAndUploadStep").tasklet(tasklet).build();
    }

    @Bean
    public Step cleanupStep(StepBuilderFactory steps) {
        Tasklet tasklet = new CleanupTasklet(Path.of("tmp"), Path.of("upload"));
        return steps.get("cleanupStep").tasklet(tasklet).build();
    }

}
