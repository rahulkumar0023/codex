package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

class XMlInvoiceReaderTest {

    @Test
    void parsesComplexInvoice() throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/complex-invoice.xml"));
        XMlInvoiceReader parser = new XMlInvoiceReader();
        InvoiceType invoice = parser.parse(xml);
        assertNotNull(invoice);
        assertEquals("TickstarAP-BIS3-test-01", invoice.getID().getValue());
    }

    @Test
    void readsInvoiceAsItemReader() throws Exception {
        XMlInvoiceReader reader = new XMlInvoiceReader();
        reader.setResource(new org.springframework.core.io.FileSystemResource("src/test/resources/complex-invoice.xml"));
        reader.open(new org.springframework.batch.item.ExecutionContext());
        InvoiceType invoice = reader.read();
        reader.close();
        assertNotNull(invoice);
        assertEquals("TickstarAP-BIS3-test-01", invoice.getID().getValue());
        // Ensure subsequent reads return null
        assertNull(reader.read());
    }
}
