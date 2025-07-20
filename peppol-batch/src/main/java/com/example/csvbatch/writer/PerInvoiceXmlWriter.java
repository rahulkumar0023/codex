package com.example.csvbatch.writer;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class PerInvoiceXmlWriter implements ItemWriter<InvoiceType> {

    @Value("${batch.output.directory:output}")
    private String outputDirectory;

    @Override
    public void write(Chunk<? extends InvoiceType> items) throws Exception {
        Path outDir = Path.of(outputDirectory);
        Files.createDirectories(outDir);
        for (InvoiceType invoice : items) {
            String id = invoice.getID() != null && invoice.getID().getValue() != null
                    ? invoice.getID().getValue() : "invoice";
            Path file = outDir.resolve(id + ".xml");
            com.example.peppol.batch.UblDocumentWriter.write(invoice, InvoiceType.class,
                    new QName("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice"), file);
        }
    }
}
