package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;

import com.example.peppol.batch.InvoiceXmlWriter;

class InvoiceXmlFileReaderTest {

    private Path tempDir;

    @BeforeEach
    void setup() throws Exception {
        tempDir = Files.createTempDirectory("invoices");
        Path sample = Path.of("src/test/resources/complex-invoice.xml");
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

    @Test
    void readsComplexInvoice() throws Exception {
        Path dir = Files.createTempDirectory("complex-invoices");
        Path sample = Path.of("src/test/resources/complex-invoice.xml");
        Files.copy(sample, dir.resolve("invoice1.xml"), StandardCopyOption.REPLACE_EXISTING);

        InvoiceXmlFileReader reader = new InvoiceXmlFileReader(dir);
        InvoiceDocument doc = reader.read();

        assertNotNull(doc);
        String expected = Files.readString(dir.resolve("invoice1.xml"));
        assertEquals(expected, doc.getXml());
    }

    @Test
    void readsComplexInvoiceAndGeneratesFileElsewhere() throws Exception {
        Path inputDir = Files.createTempDirectory("complex-invoices-in");
        Path sample = Path.of("src/test/resources/complex-invoice.xml");
        Path inputFile = inputDir.resolve("invoice1.xml");
        Files.copy(sample, inputFile, StandardCopyOption.REPLACE_EXISTING);

        InvoiceXmlFileReader reader = new InvoiceXmlFileReader(inputDir);
        InvoiceDocument doc = reader.read();
        assertNotNull(doc);

        Path outputDir = Files.createTempDirectory("complex-invoices-out");
        InvoiceXmlWriter writer = new InvoiceXmlWriter(outputDir);
        Chunk<InvoiceDocument> chunk = new Chunk<>();
        chunk.add(doc);
        writer.write(chunk);

        Path written = outputDir.resolve("invoice1.xml");
        assertTrue(Files.exists(written));
        assertEquals(Files.readString(sample), Files.readString(written));
    }
}
