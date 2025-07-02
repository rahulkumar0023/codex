package com.example.peppol.batch;

import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


class InvoiceXmlWriterTest {

    @Test
    void writesInvoiceXmlToFile() throws Exception {
        // Create a temporary output directory
        Path outputDir = Files.createTempDirectory("invoices-test");

        // Load sample XML content
        String xml = Files.readString(Path.of("src/test/resources/complex-invoice.xml"));

        // Create an InvoiceDocument
        Path sourceFile = Path.of("invoice1.xml");


        InvoiceDocument doc = new InvoiceDocument(xml, sourceFile);

        // Wrap the document in a Chunk
        Chunk<InvoiceDocument> chunk = new Chunk<>();
        chunk.add(doc);

        // Create writer and invoke
        InvoiceXmlWriter writer = new InvoiceXmlWriter(outputDir);
        writer.write(chunk);

        // Assert the output file exists and content matches
        Path written = outputDir.resolve("invoice1.xml");
        System.out.println("Written: " + written.toAbsolutePath());
        assertTrue(Files.exists(written));
        assertEquals(xml, Files.readString(written));
    }
    @Test
    void writesComplexInvoiceXmlToFile() throws Exception {
        Path outputDir = Files.createTempDirectory("complex-invoices-test");

        String xml = Files.readString(Path.of("src/test/resources/complex-invoice.xml"));

        InvoiceDocument doc = new InvoiceDocument(xml, Path.of("invoice1.xml"));

        Chunk<InvoiceDocument> chunk = new Chunk<>();
        chunk.add(doc);

        InvoiceXmlWriter writer = new InvoiceXmlWriter(outputDir);
        writer.write(chunk);

        Path written = outputDir.resolve("invoice1.xml");
        assertTrue(Files.exists(written));
        assertEquals(xml, Files.readString(written));
    }
}
