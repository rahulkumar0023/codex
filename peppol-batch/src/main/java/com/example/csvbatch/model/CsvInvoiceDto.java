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
    private String buyerReference;
    private String startDate;
    private String endDate;
    private String contractDocumentReferenceCbcId;

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
    private String customerRegistrationName;


    private String paymentMeansCode;
    private String paymentMeansCbcPaymentId;

    private String legalMonetaryTotalCbcLineExtensionAmount;
    private String legalMonetaryTotalCbcTaxExclusiveAmount;
    private String legalMonetaryTotalCbcTaxInclusiveAmount;
    private String legalMonetaryTotalCbcPayableAmount;

    private String invoiceLineCbcId;
    private String invoiceLineCbcInvoicedQuantity;
    private String invoiceLineCbcLineExtensionAmount;

    private String currencyId;
    private String itemCbcName;
    private String invoicePeriodCbcStartDate;
    private String invoicePeriodCbcEndDate;
    private String descriptionCbcItem;

    private String allowanceChargeCbcChargeIndicator;
    private String allowanceChargeCbcAmount;
    private String allowanceChargeCbcBaseAmount;
    private String baseAmountCbcCurrencyId;
    private String allowanceChargeReason;
}