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
                .invoicePeriodStartDate("2023-11-01")
                .invoicePeriodEndDate("2032-12-31")
                .contractDocumentReferenceID("framework no 1")
                .additionalDocumentReference1ID("DR35141")
                .additionalDocumentReference1DocumentTypeCode("130")
                .additionalDocumentReference2ID("ts12345")
                .additionalDocumentReference2DocumentDescription("Technical specification")
                .additionalDocumentReference2AttachmentURI("www.techspec.no")
                .paymentMeansPaymentMeansCode("30")
                .paymentMeansPaymentID("Snippet1")
                .paymentMeansPayeeFinancialAccountID("IBAN32423940")
                .paymentMeansPayeeFinancialAccountName("AccountName")
                .paymentMeansPayeeFinancialAccountBranchID("BIC324098")
                .paymentTermsNote("Payment within 10 days, 2% discount")
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

        assertEquals("2023-11-01", invoice.getInvoicePeriod().get(0).getStartDate().getValue().toString());
        assertEquals("framework no 1", invoice.getContractDocumentReference().get(0).getID().getValue());
        assertEquals("DR35141", invoice.getAdditionalDocumentReference().get(0).getID().getValue());
        assertEquals("ts12345", invoice.getAdditionalDocumentReference().get(1).getID().getValue());
        assertEquals("30", invoice.getPaymentMeans().get(0).getPaymentMeansCode().getValue());
        assertEquals("Snippet1", invoice.getPaymentMeans().get(0).getPaymentID().get(0).getValue());
        assertEquals("IBAN32423940", invoice.getPaymentMeans().get(0).getPayeeFinancialAccount().getID().getValue());
        assertEquals("AccountName", invoice.getPaymentMeans().get(0).getPayeeFinancialAccount().getName().getValue());
        assertEquals("BIC324098", invoice.getPaymentMeans().get(0).getPayeeFinancialAccount().getFinancialInstitutionBranch().getID().getValue());
        assertEquals("Payment within 10 days, 2% discount", invoice.getPaymentTerms().get(0).getNote().get(0).getValue());
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
