package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.example.peppol.batch.dto.InvoiceDocument;
import com.example.peppol.batch.mapper.InvoiceDocumentMapper;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

/**
 * Simple round-trip test for {@link InvoiceDocumentMapper}.
 */
class InvoiceDocumentMapperTest {
    @Test
    void mapsDtoToInvoiceAndBack() {
        InvoiceDocument doc = new InvoiceDocument();
        doc.setInvoiceNumber("INV-001");
        doc.setIssueDate("2024-07-15");
        doc.setDueDate("2024-08-14");
        doc.setSupplierCityName("Supplier City");
        doc.setInvoiceLineCbcId("1");
        doc.setInvoiceLineCbcInvoicedQuantity("5");
        doc.setInvoiceLineCbcLineExtensionAmount("100.00");
        doc.setCurrencyId("EUR");
        doc.setItemCbcName("Test Item");
        doc.setAllowanceChargeCbcChargeIndicator("true");
        doc.setAllowanceChargeCbcAllowanceChargeReason("Discount");
        doc.setAllowanceChargeCbcAmount("5.00");
        doc.setBaseAmountCbcCurrencyId("EUR");
        doc.setAllowanceChargeCbcBaseAmount("100.00");

        InvoiceDocumentMapper mapper = Mappers.getMapper(InvoiceDocumentMapper.class);
        InvoiceType invoice = mapper.toInvoice(doc);
        assertNotNull(invoice);
        assertEquals("INV-001", invoice.getID().getValue());
        assertEquals("2024-07-15", invoice.getIssueDate().getValue().toString());
        assertEquals("Supplier City", invoice.getAccountingSupplierParty().getParty().getPostalAddress().getCityName().getValue());
        assertEquals("1", invoice.getInvoiceLine().get(0).getID().getValue());

        InvoiceDocument roundtrip = mapper.fromInvoice(invoice);
        assertEquals(doc.getInvoiceNumber(), roundtrip.getInvoiceNumber());
        assertEquals(doc.getIssueDate(), roundtrip.getIssueDate());
        assertEquals(doc.getSupplierCityName(), roundtrip.getSupplierCityName());
        assertEquals(doc.getInvoiceLineCbcId(), roundtrip.getInvoiceLineCbcId());
    }
}
