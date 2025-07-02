package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

class UblInvoiceParserTest {

    @Test
    void parsesComplexInvoice() throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/complex-invoice.xml"));
        UblInvoiceParser parser = new UblInvoiceParser();
        InvoiceType invoice = parser.parse(xml);
        assertNotNull(invoice);
        assertEquals("TickstarAP-BIS3-test-01", invoice.getID().getValue());
    }
}
