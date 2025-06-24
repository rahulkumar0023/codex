package com.example.peppol.batch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

/**
 * Writes invoice XML to files.
 */
public class InvoiceXmlWriter implements ItemWriter<InvoiceDocument> {

    private final Path outputDir;

    public InvoiceXmlWriter(Path outputDir) {
        this.outputDir = outputDir;
        try {
            Files.createDirectories(outputDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create output directory", e);
        }
    }

    @Override
    public void write(List<? extends InvoiceDocument> items) throws Exception {
        for (InvoiceDocument doc : items) {
            Path input = doc.getSourceFile();
            String fileName = input.getFileName().toString();
            int idx = fileName.lastIndexOf('.');
            String baseName = idx >= 0 ? fileName.substring(0, idx) : fileName;
            Path out = outputDir.resolve(baseName + ".xml");
            Files.writeString(out, doc.getXml(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
}
