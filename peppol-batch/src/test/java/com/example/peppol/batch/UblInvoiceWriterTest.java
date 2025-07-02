package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

class UblInvoiceWriterTest {

    @Test
    void writesInvoiceToXmlString() throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/complex-invoice.xml"));
        UblInvoiceParser parser = new UblInvoiceParser();
        InvoiceType invoice = parser.parse(xml);

        UblInvoiceWriter writer = new UblInvoiceWriter();
        String out = writer.writeToString(invoice);

        assertNotNull(out);
        assertFalse(out.isEmpty());

        InvoiceType parsed = parser.parse(out);
        assertEquals(invoice.getID().getValue(), parsed.getID().getValue());
        assertTrue(out.contains("<Invoice xmlns=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2\""));
    }

    @Test
    void writesInvoiceToFile() throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/complex-invoice.xml"));
        UblInvoiceParser parser = new UblInvoiceParser();
        InvoiceType invoice = parser.parse(xml);

        UblInvoiceWriter writer = new UblInvoiceWriter();
        Path out = Files.createTempFile("invoice", ".xml");
        writer.write(invoice, out);

        String written = Files.readString(out);
        InvoiceType parsed = parser.parse(written);
        assertEquals(invoice.getID().getValue(), parsed.getID().getValue());
    }
}
