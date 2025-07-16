package com.example.peppol.batch.dto;

import lombok.Data;

/**
 * Data transfer object representing the flat invoice information
 * coming from the CSV file. Only green and white fields from the
 * original mapping are included.
 */
@Data
public class InvoiceDocument {
    // Top-level fields
    private String invoiceNumber;        // /cbc:ID
    private String issueDate;            // /cbc:IssueDate
    private String dueDate;              // /cbc:DueDate
    private String invoiceTypeCode;      // /cbc:InvoiceTypeCode
    private String note;                 // /cbc:Note
    private String buyerReference;       // /cbc:BuyerReference

    // Invoice period
    private String startDate;            // /cac:InvoicePeriod/cbc:StartDate
    private String endDate;              // /cac:InvoicePeriod/cbc:EndDate

    // Contract/Document reference
    private String contractDocumentReferenceCbcId; // /cac:ContractDocumentReference/cbc:ID

    // Supplier party info
    private String supplierEndPoint;
    private String supplierPartyIdentificationCbcId;
    private String supplierPartyNameCbcName;
    private String supplierStreetName;
    private String supplierAdditionalStreetName;
    private String supplierCityName;
    private String supplierPostalZone;
    private String supplierCountrySubentity;
    private String supplierAddressLineCbcLine;
    private String supplierCountryCbcIdentificationCode;
    private String supplierPartyTaxSchemeCompanyId;
    private String supplierPartyTaxSchemeTaxSchemeId;
    private String supplierPartyLegalEntityRegistrationName;
    private String supplierPartyLegalEntityCompanyId;
    private String supplierPartyLegalEntityCompanyLegalForm;

    // Customer party info
    private String customerEndPoint;
    private String customerPartyIdentificationCbcId;
    private String customerPartyNameCbcName;
    private String customerStreetName;
    private String customerAdditionalStreetName;
    private String customerCityName;
    private String customerPostalZone;
    private String customerCountrySubentity;
    private String customerAddressLineCbcLine;
    private String customerCountryCbcIdentificationCode;
    private String customerPartyLegalEntityRegistrationName;
    private String customerPartyLegalEntityCompanyId;
    private String customerContactName;
    private String customerContactTelephone;
    private String customerContactElectronicMail;

    // Payment and delivery
    private String paymentMeans;
    private String paymentMeansCbcPaymentId;
    private String legalMonetaryTotalCbcLineExtensionAmount;
    private String legalMonetaryTotalCbcTaxExclusiveAmount;
    private String legalMonetaryTotalCbcTaxInclusiveAmount;
    private String legalMonetaryTotalCbcPayableAmount;

    // Invoice line info
    private String invoiceLineCbcId;
    private String invoiceLineCbcInvoicedQuantity;
    private String invoiceLineCbcLineExtensionAmount;
    private String currencyId;
    private String itemCbcName;
    private String invoicePeriodCbcStartDate;
    private String invoicePeriodCbcEndDate;
    private String descriptionCbcItem;

    // Allowance/charge
    private String allowanceChargeCbcChargeIndicator;
    private String allowanceChargeCbcAllowanceChargeReason;
    private String allowanceChargeCbcAmount;
    private String baseAmountCbcCurrencyId;
    private String allowanceChargeCbcBaseAmount;
}
