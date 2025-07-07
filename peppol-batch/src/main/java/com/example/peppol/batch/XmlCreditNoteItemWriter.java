package com.example.peppol.batch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import network.oxalis.peppol.ubl2.jaxb.CreditNoteType;

/**
 * Simple {@link ItemWriter} adapter for {@link UblCreditNoteWriter}.
 */
public class XmlCreditNoteItemWriter implements ItemWriter<CreditNoteType> {

    private final Path outputDir;
    private final UblCreditNoteWriter delegate = new UblCreditNoteWriter();
    private final AtomicInteger counter = new AtomicInteger();

    public XmlCreditNoteItemWriter(Path outputDir) {
        this.outputDir = outputDir;
        try {
            Files.createDirectories(outputDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create output directory", e);
        }
    }

    @Override
    public void write(Chunk<? extends CreditNoteType> items) throws Exception {
        for (CreditNoteType cn : items) {
            String baseName;
            if (cn.getID() != null && cn.getID().getValue() != null && !cn.getID().getValue().isBlank()) {
                baseName = cn.getID().getValue();
            } else {
                baseName = "creditnote-" + counter.incrementAndGet();
            }
            if (!baseName.contains("CN")) {
                baseName = "CN-" + baseName;
            }
            Path out = outputDir.resolve(baseName + ".xml");
            delegate.write(cn, out);
        }
    }
}
