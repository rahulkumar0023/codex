package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.file.Files;
import java.nio.file.Path;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

@Slf4j
class UblInvoiceWriterTest {

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
        log.info("parsed.getID().getValue(): {}", parsed.getID().getValue());
        assertEquals(invoice.getID().getValue(), parsed.getID().getValue());
    }
}
