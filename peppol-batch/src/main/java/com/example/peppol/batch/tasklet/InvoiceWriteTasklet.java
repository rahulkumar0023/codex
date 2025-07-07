package com.example.peppol.batch.tasklet;

import com.example.peppol.batch.InvoiceRecord;
import com.example.peppol.batch.XmlInvoiceWriter;
import java.nio.file.Path;
import java.util.List;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

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
        List<InvoiceRecord> records = (List<InvoiceRecord>) chunkContext.getStepContext()
                .getStepExecution().getJobExecution().getExecutionContext().get("invoices");
        if (records != null && !records.isEmpty()) {
            XmlInvoiceWriter writer = new XmlInvoiceWriter();
            for (InvoiceRecord r : records) {
                Path input = r.getSourceFile();
                String fileName = input.getFileName().toString();
                int idx = fileName.lastIndexOf('.');
                String baseName = idx >= 0 ? fileName.substring(0, idx) : fileName;
                Path out = outputDir.resolve(baseName + ".xml");
                writer.write(r.getInvoice(), out);
            }
        }
        return RepeatStatus.FINISHED;
    }
}
