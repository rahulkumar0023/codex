package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

@Slf4j
class UblInvoiceWriterTest {

    @Test
    void writesInvoiceToXmlString() throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/complex-invoice.xml"));
        UblInvoiceParser parser = new UblInvoiceParser();
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
    }

}
