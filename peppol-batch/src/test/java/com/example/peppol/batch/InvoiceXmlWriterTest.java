package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InvoiceXmlWriterTest {

    private Path outputDir;

    @BeforeEach
    void setup() throws Exception {
        outputDir = Files.createTempDirectory("out");
    }

    @Test
    void writesInvoiceXmlToFile() throws Exception {
        InvoiceXmlWriter writer = new InvoiceXmlWriter(outputDir);
        String xml = Files.readString(Path.of("src/test/resources/sample-invoice.xml"));
        InvoiceDocument doc = new InvoiceDocument(xml, Path.of("invoice1.xml"));
        writer.write(java.util.List.of(doc));
        Path written = outputDir.resolve("invoice1.xml");
        assertTrue(Files.exists(written));
        assertEquals(xml, Files.readString(written));
    }
}
