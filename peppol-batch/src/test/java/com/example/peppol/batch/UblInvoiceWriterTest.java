package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

import com.example.peppol.batch.XMlInvoiceReader;

@Slf4j
class UblInvoiceWriterTest {

    @Test
    void writesInvoiceToXmlString() throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/complex-invoice.xml"));
        XMlInvoiceReader parser = new XMlInvoiceReader();
        InvoiceType invoice = parser.parse(xml);

        UblInvoiceWriter writer = new UblInvoiceWriter();
        String out = writer.writeToString(invoice);

        Path outFile = Path.of("target", "generated-invoice.xml");
        Files.createDirectories(outFile.getParent());
        writer.write(invoice, outFile);
        log.info("Written invoice to {}", outFile.toAbsolutePath());

        assertNotNull(out);
        assertFalse(out.isEmpty());

        InvoiceType parsed = parser.parse(out);

        log.info("parsed.getID().getValue(): {}", parsed.getID().getValue());
        assertEquals(invoice.getID().getValue(), parsed.getID().getValue());
        assertTrue(out.contains("<Invoice xmlns=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2\""));
        assertFalse(out.contains("CommonExtensionComponents-2"));
    }

    @Test
    void writesInvoiceUsingItemWriter() throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/complex-invoice.xml"));
        XMlInvoiceReader parser = new XMlInvoiceReader();
        InvoiceType invoice = parser.parse(xml);

        Path outputDir = Files.createTempDirectory("invoice-item-writer");
        UblInvoiceWriter writer = new UblInvoiceWriter(outputDir);
        Chunk<InvoiceType> chunk = new Chunk<>();
        chunk.add(invoice);
        writer.write(chunk);

        Path written = outputDir.resolve(invoice.getID().getValue() + ".xml");
        assertTrue(Files.exists(written));

        String writtenXml = Files.readString(written);
        InvoiceType parsed = parser.parse(writtenXml);
        assertEquals(invoice.getID().getValue(), parsed.getID().getValue());
    }

}
