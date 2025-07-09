package com.example.peppol.batch.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

class CsvInvoiceProcessorTest {

    @Test
    void mapsRecordToInvoice() throws Exception {
        java.util.Map<String,String> fields = new java.util.LinkedHashMap<>();
        fields.put("CustomizationID", "urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0");
        fields.put("ProfileID", "urn:fdc:peppol.eu:2017:poacc:billing:01:1.0");
        fields.put("ID", "TickstarAP-BIS3-test-01");
        fields.put("IssueDate", "2023-12-19");
        fields.put("DueDate", "2024-01-18");
        fields.put("InvoiceTypeCode", "380");
        fields.put("Note", "GalaxyGateway hosted AP BIS3 Billing Test file");
        fields.put("TaxPointDate", "2023-12-19");
        fields.put("DocumentCurrencyCode", "EUR");
        fields.put("TaxCurrencyCode", "SEK");
        fields.put("AdditionalItemProperty_cbc_chassisValue", "CHS123");
        for (int i = 12; i <= 67; i++) {
            fields.put("f" + i, "p" + i);
        }
        CsvInvoiceRecord rec = CsvInvoiceRecord.builder()
                .fields(fields)
                .build();
        CsvInvoiceProcessor processor = new CsvInvoiceProcessor();
        InvoiceType invoice = processor.process(rec);
        assertNotNull(invoice);
        assertEquals("TickstarAP-BIS3-test-01", invoice.getID().getValue());
        assertEquals("urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0",
                invoice.getCustomizationID().getValue());
        assertEquals("CHS123",
                invoice.getInvoiceLine().get(0).getItem().getAdditionalItemProperty().get(0).getValue().getValue());
    }
}
