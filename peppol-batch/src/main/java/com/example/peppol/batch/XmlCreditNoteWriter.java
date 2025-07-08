package com.example.peppol.batch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.namespace.QName;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import network.oxalis.peppol.ubl2.jaxb.CreditNoteType;

/**
 * Utility to write {@link CreditNoteType} instances to XML and act as a
 * Spring Batch {@link ItemWriter} when configured with an output directory.
 */
public class XmlCreditNoteWriter implements ItemWriter<CreditNoteType> {

    private final Path outputDir;
    private final AtomicInteger counter = new AtomicInteger();

    /**
     * Create a writer without an output directory. The {@link #write(Chunk)}
     * method cannot be used in this case but {@link #write(CreditNoteType, Path)}
     * still works.
     */
    public XmlCreditNoteWriter() {
        this.outputDir = null;
    }

    /**
     * Create a writer that outputs credit notes to the given directory.
     *
     * @param outputDir directory where credit note XML files will be written
     */
    public XmlCreditNoteWriter(Path outputDir) {
        this.outputDir = outputDir;
        try {
            Files.createDirectories(outputDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create output directory", e);
        }
    }

    /**
     * Marshal the given credit note to a formatted XML string.
     *
     * @param creditNote the credit note object
     * @return XML representation
     */
    public String writeToString(CreditNoteType creditNote) {
        return UblDocumentWriter.writeToString(
                creditNote,
                CreditNoteType.class,
                new QName("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote"));
    }

    /**
     * Marshal the credit note to the given file path.
     *
     * @param creditNote the credit note to marshal
     * @param output  the target file path
     */
    public void write(CreditNoteType creditNote, Path output) {
        UblDocumentWriter.write(
                creditNote,
                CreditNoteType.class,
                new QName("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote"),
                output);
    }

    @Override
    public void write(Chunk<? extends CreditNoteType> items) throws Exception {
        if (outputDir == null) {
            throw new IllegalStateException("Output directory not configured");
        }
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
            write(cn, out);
        }
    }
}
