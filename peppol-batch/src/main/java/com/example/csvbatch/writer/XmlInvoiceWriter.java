package com.example.csvbatch.writer;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class XmlInvoiceWriter implements ItemWriter<InvoiceType> {

    private final Path outputDir;

    public XmlInvoiceWriter() {
        this.outputDir = null;
    }

    public XmlInvoiceWriter(Path outputDir) {
        this.outputDir = outputDir;
        try {
            Files.createDirectories(outputDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create output directory", e);
        }
    }

    public String writeToString(InvoiceType invoice) {
        return UblDocumentWriter.writeToString(
                invoice,
                InvoiceType.class,
                new QName("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice"));
    }

    public void write(InvoiceType invoice, Path output) {
        UblDocumentWriter.write(
                invoice,
                InvoiceType.class,
                new QName("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice"),
                output);
    }

    @Override
    public void write(Chunk<? extends InvoiceType> items) throws Exception {
        if (outputDir == null) {
            throw new IllegalStateException("Output directory not configured");
        }
        int counter = 0;
        for (InvoiceType invoice : items) {
            String baseName;
            if (invoice.getID() != null && invoice.getID().getValue() != null
                    && !invoice.getID().getValue().isBlank()) {
                baseName = invoice.getID().getValue();
            } else {
                baseName = "invoice-" + (++counter);
            }
            Path out = outputDir.resolve(baseName + ".xml");
            write(invoice, out);
        }
    }
}
