package com.example.peppol.batch.tasklet;

import com.example.peppol.batch.InvoiceDocument;
import com.example.peppol.batch.InvoiceXmlFileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Tasklet that reads invoice XML files and stores them in the job context.
 */
public class InvoiceReadTasklet implements Tasklet {

    private final Path inputDir;

    public InvoiceReadTasklet(Path inputDir) {
        this.inputDir = inputDir;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        InvoiceXmlFileReader reader = new InvoiceXmlFileReader(inputDir);
        List<InvoiceDocument> docs = new ArrayList<>();
        InvoiceDocument doc;
        while ((doc = reader.read()) != null) {
            docs.add(doc);
        }
        chunkContext.getStepContext().getStepExecution()
                .getJobExecution().getExecutionContext().put("invoices", docs);
        return RepeatStatus.FINISHED;
    }
}
