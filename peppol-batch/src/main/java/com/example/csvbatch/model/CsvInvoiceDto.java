package com.example.csvbatch.model;

import lombok.Data;

/**
 * Simple DTO representing invoice data from CSV.
 */
@Data
public class CsvInvoiceDto {
    private String ublVersionID;
    private String customizationID;
    private String profileID;
    private String invoiceNumber;
    private String uuid;
    private String issueDate;
    private String dueDate;
    private String invoiceTypeCode;
    private String note;
    private String documentCurrencyCode;
    private String lineCountNumeric;

    private String supplierCompanyId;
    private String supplierName;
    private String supplierStreet;
    private String supplierCity;
    private String supplierPostal;
    private String supplierCountryCode;
    private String supplierVatId;
    private String supplierLegalId;
    private String supplierTelephone;

    private String customerCompanyId;
    private String customerName;
    private String customerStreet;
    private String customerCity;
    private String customerPostal;
    private String customerCountryCode;
    private String customerVatId;
    private String customerLegalId;

    private String paymentMeansCode;
    private String paymentDueDate;
    private String paymentId;
    private String payeeAccountId;
    private String payeeAccountName;

    private String taxTotalAmount;
    private String taxSubtotalTaxableAmount;
    private String taxSubtotalTaxAmount;
    private String taxSubtotalPercent;
    private String taxCategoryID;
    private String taxCategoryName;
    private String taxCategoryPercent;
    private String taxSchemeID;

    private String lineExtensionAmount;
    private String taxExclusiveAmount;
    private String taxInclusiveAmount;
    private String payableAmount;

    private String invoiceLineId;
    private String invoiceLineUuid;
    private String invoiceLineNote1;
    private String invoiceLineNote2;
    private String invoiceLineQuantity;
    private String invoiceLineUnitCode;
    private String invoiceLineLineExtensionAmount;
    private String invoiceLineTaxAmount;
    private String invoiceLineTaxableAmount;
    private String invoiceLineTaxPercent;
    private String invoiceLineTaxCategoryId;
    private String invoiceLineTaxCategoryName;
    private String invoiceLineTaxCategoryPercent;
    private String invoiceLineTaxSchemeId;
    private String invoiceLineItemName;
    private String invoiceLineItemId;
    private String invoiceLinePriceAmount;
}