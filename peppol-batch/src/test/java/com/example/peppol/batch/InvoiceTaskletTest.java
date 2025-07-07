package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.peppol.batch.tasklet.InvoiceReadTasklet;
import com.example.peppol.batch.tasklet.InvoiceWriteTasklet;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.repeat.RepeatStatus;

class InvoiceTaskletTest {

    @Test
    void readsAndWritesInvoiceUsingTasklets() throws Exception {
        Path inputDir = Files.createTempDirectory("tasklet-in");
        Path sample = Path.of("src/test/resources/complex-invoice.xml");
        Files.copy(sample, inputDir.resolve("invoice1.xml"));
        Path credit = Path.of("src/test/resources/sample-creditnote.xml");
        Files.copy(credit, inputDir.resolve("credit1.xml"));

        Path outputDir = Files.createTempDirectory("tasklet-out");

        JobExecution jobExecution = new JobExecution(1L, new JobParameters());
        StepExecution readExecution = new StepExecution("readStep", jobExecution);
        ChunkContext readContext = new ChunkContext(new StepContext(readExecution));
        StepContribution readContribution = new StepContribution(readExecution);

        InvoiceReadTasklet readTasklet = new InvoiceReadTasklet(inputDir);
        readTasklet.execute(readContribution, readContext);

        var ctx = jobExecution.getExecutionContext();
        assertEquals(1, ((java.util.List<?>) ctx.get("invoices")).size());
        assertEquals(1, ((java.util.List<?>) ctx.get("creditNotes")).size());

        StepExecution writeExecution = new StepExecution("writeStep", jobExecution);
        ChunkContext writeContext = new ChunkContext(new StepContext(writeExecution));
        StepContribution writeContribution = new StepContribution(writeExecution);

        InvoiceWriteTasklet writeTasklet = new InvoiceWriteTasklet(outputDir);
        writeTasklet.execute(writeContribution, writeContext);

        assertTrue(Files.exists(outputDir.resolve("invoice1.xml")));
    }
}
