package com.example.peppol.batch;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility to write {@link InvoiceType} instances to XML.
 */
public class XmlInvoiceWriter implements ItemWriter<InvoiceType> {

    private final Path outputDir;

    /**
     * Create a writer without an output directory. The {@link #write(Chunk)}
     * method cannot be used in this case but {@link #write(InvoiceType, Path)}
     * still works.
     */
    public XmlInvoiceWriter() {
        this.outputDir = null;
    }

    /**
     * Create a writer that outputs invoices to the given directory.
     *
     * @param outputDir directory where invoice XML files will be written
     */
    public XmlInvoiceWriter(Path outputDir) {
        this.outputDir = outputDir;
        try {
            Files.createDirectories(outputDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create output directory", e);
        }
    }

    /**
     * Marshal the given invoice to a formatted XML string.
     *
     * @param invoice the invoice object
     * @return XML representation
     */
    public String writeToString(InvoiceType invoice) {
        return UblDocumentWriter.writeToString(
                invoice,
                InvoiceType.class,
                new QName("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice"));
    }

    /**
     * Marshal the invoice to the given file path.
     *
     * @param invoice the invoice to marshal
     * @param output  the target file path
     */
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
