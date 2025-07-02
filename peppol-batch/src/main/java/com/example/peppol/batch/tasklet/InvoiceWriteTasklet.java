package com.example.peppol.batch.tasklet;

import com.example.peppol.batch.InvoiceDocument;
import com.example.peppol.batch.InvoiceXmlWriter;
import java.nio.file.Path;
import java.util.List;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.item.Chunk;

/**
 * Tasklet that writes invoices stored in the job context to files.
 */
public class InvoiceWriteTasklet implements Tasklet {

    private final Path outputDir;

    public InvoiceWriteTasklet(Path outputDir) {
        this.outputDir = outputDir;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<InvoiceDocument> docs = (List<InvoiceDocument>) chunkContext.getStepContext()
                .getStepExecution().getJobExecution().getExecutionContext().get("invoices");
        if (docs != null && !docs.isEmpty()) {
            InvoiceXmlWriter writer = new InvoiceXmlWriter(outputDir);
            Chunk<InvoiceDocument> chunk = new Chunk<>();
            chunk.addAll(docs);
            writer.write(chunk);
        }
        return RepeatStatus.FINISHED;
    }
}
