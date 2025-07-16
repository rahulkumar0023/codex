package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.example.peppol.batch.dto.InvoiceDocument;
import com.example.peppol.batch.mapper.CsvToInvoiceMapper;
import com.example.peppol.batch.mapper.InvoiceToCsvMapper;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

/**
 * Simple round-trip test for the invoice mappers.
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

        CsvToInvoiceMapper toMapper = Mappers.getMapper(CsvToInvoiceMapper.class);
        InvoiceType invoice = toMapper.toInvoice(doc);
        assertNotNull(invoice);
        assertEquals("INV-001", invoice.getID().getValue());
        assertEquals("2024-07-15", invoice.getIssueDate().getValue().toString());
        assertEquals("Supplier City", invoice.getAccountingSupplierParty().getParty().getPostalAddress().getCityName().getValue());
        assertEquals("1", invoice.getInvoiceLine().get(0).getID().getValue());

        InvoiceToCsvMapper fromMapper = Mappers.getMapper(InvoiceToCsvMapper.class);
        InvoiceDocument roundtrip = fromMapper.fromInvoice(invoice);
        assertEquals(doc.getInvoiceNumber(), roundtrip.getInvoiceNumber());
        assertEquals(doc.getIssueDate(), roundtrip.getIssueDate());
        assertEquals(doc.getSupplierCityName(), roundtrip.getSupplierCityName());
        assertEquals(doc.getInvoiceLineCbcId(), roundtrip.getInvoiceLineCbcId());
    }
}
