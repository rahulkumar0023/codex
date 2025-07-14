package com.example.peppol.batch.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.jupiter.api.Test;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

class CsvInvoiceProcessorTest {

    @Test
    void mapsRecordToInvoice() throws Exception {
        CsvInvoiceRecord rec = CsvInvoiceRecord.builder()
                .customizationID("urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0")
                .profileID("urn:fdc:peppol.eu:2017:poacc:billing:01:1.0")
                .id("TickstarAP-BIS3-test-01")
                .issueDate("2023-12-19")
                .dueDate("2024-01-18")
                .invoiceTypeCode("380")
                .note("GalaxyGateway hosted AP BIS3 Billing Test file")
                .taxPointDate("2023-12-19")
                .documentCurrencyCode("EUR")
                .taxCurrencyCode("SEK")
                .build();
        CsvInvoiceProcessor processor = new CsvInvoiceProcessor();
        InvoiceType invoice = processor.process(rec);
        assertNotNull(invoice);
        assertEquals("TickstarAP-BIS3-test-01", invoice.getID().getValue());
        assertEquals("urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0",
                invoice.getCustomizationID().getValue());
        XMLGregorianCalendar issue = toXmlDate("2023-12-19");
        XMLGregorianCalendar due = toXmlDate("2024-01-18");
        XMLGregorianCalendar tax = toXmlDate("2023-12-19");
        assertEquals(issue, invoice.getIssueDate().getValue());
        assertEquals(due, invoice.getDueDate().getValue());
        assertEquals(tax, invoice.getTaxPointDate().getValue());
        assertEquals("EUR", invoice.getDocumentCurrencyCode().getValue());
        assertEquals("SEK", invoice.getTaxCurrencyCode().getValue());
        assertNotNull(invoice.getUBLExtensions());
        assertTrue(invoice.getUBLExtensions().getUBLExtension().isEmpty());
    }

    private XMLGregorianCalendar toXmlDate(String value) throws DatatypeConfigurationException {
        java.time.LocalDate local = java.time.LocalDate.parse(value);
        return DatatypeFactory.newInstance()
                .newXMLGregorianCalendarDate(
                        local.getYear(),
                        local.getMonthValue(),
                        local.getDayOfMonth(),
                        DatatypeConstants.FIELD_UNDEFINED);
    }
}
