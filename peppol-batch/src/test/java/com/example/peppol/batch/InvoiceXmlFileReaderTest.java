package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InvoiceXmlFileReaderTest {

    private Path tempDir;

    @BeforeEach
    void setup() throws Exception {
        tempDir = Files.createTempDirectory("invoices");
        Path sample = Path.of("src/test/resources/sample-invoice.xml");
        Files.copy(sample, tempDir.resolve("invoice1.xml"), StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void readsXmlInvoice() throws Exception {
        InvoiceXmlFileReader reader = new InvoiceXmlFileReader(tempDir);
        InvoiceDocument doc = reader.read();
        assertNotNull(doc);
        String expected = Files.readString(tempDir.resolve("invoice1.xml"));
        assertEquals(expected, doc.getXml());
    }
}
