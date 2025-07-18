package com.example.peppol.batch;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple POJO representing invoice data extracted from various sources.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDocument {
    @CsvBindByName(column = "invoiceNumber")
    private String invoiceNumber;
    @CsvBindByName(column = "issueDate")
    private String issueDate;
    @CsvBindByName(column = "dueDate")
    private String dueDate;
    @CsvBindByName(column = "invoiceTypeCode")
    private String invoiceTypeCode;
    @CsvBindByName(column = "note")
    private String note;
    @CsvBindByName(column = "buyerReference")
    private String buyerReference;
    @CsvBindByName(column = "startDate")
    private String startDate;
    @CsvBindByName(column = "endDate")
    private String endDate;
    @CsvBindByName(column = "contractDocumentReferenceCbcId")
    private String contractDocumentReferenceCbcId;
    @CsvBindByName(column = "supplierEndPoint")
    private String supplierEndPoint;
    @CsvBindByName(column = "supplierPartyIdentificationCbcId")
    private String supplierPartyIdentificationCbcId;
    @CsvBindByName(column = "supplierPartyNameCbcName")
    private String supplierPartyNameCbcName;
    @CsvBindByName(column = "supplierStreetName")
    private String supplierStreetName;
    @CsvBindByName(column = "supplierAdditionalStreetName")
    private String supplierAdditionalStreetName;
    @CsvBindByName(column = "supplierCityName")
    private String supplierCityName;
    @CsvBindByName(column = "supplierPostalZone")
    private String supplierPostalZone;
    @CsvBindByName(column = "supplierCountrySubentity")
    private String supplierCountrySubentity;
    @CsvBindByName(column = "supplierAddressLineCbcLine")
    private String supplierAddressLineCbcLine;
    @CsvBindByName(column = "supplierCountryCbcIdentificationCode")
    private String supplierCountryCbcIdentificationCode;
    @CsvBindByName(column = "customerEndPoint")
    private String customerEndPoint;
    @CsvBindByName(column = "customerPartyIdentificationCbcId")
    private String customerPartyIdentificationCbcId;
    @CsvBindByName(column = "customerPartyNameCbcName")
    private String customerPartyNameCbcName;
    @CsvBindByName(column = "customerStreetName")
    private String customerStreetName;
    @CsvBindByName(column = "customerAdditionalStreetName")
    private String customerAdditionalStreetName;
    @CsvBindByName(column = "customerCityName")
    private String customerCityName;
    @CsvBindByName(column = "customerPostalZone")
    private String customerPostalZone;
    @CsvBindByName(column = "customerCountrySubentity")
    private String customerCountrySubentity;
    @CsvBindByName(column = "customerAddressLineCbcLine")
    private String customerAddressLineCbcLine;
    @CsvBindByName(column = "customerCountryCbcIdentificationCode")
    private String customerCountryCbcIdentificationCode;
    @CsvBindByName(column = "customerRegistrationName")
    private String customerRegistrationName;
    @CsvBindByName(column = "paymentMeans")
    private String paymentMeans;
    @CsvBindByName(column = "paymentMeansCbcPaymentId")
    private String paymentMeansCbcPaymentId;
    @CsvBindByName(column = "legalMonetaryTotalCbcLineExtensionAmount")
    private String legalMonetaryTotalCbcLineExtensionAmount;
    @CsvBindByName(column = "legalMonetaryTotalCbcTaxExclusiveAmount")
    private String legalMonetaryTotalCbcTaxExclusiveAmount;
    @CsvBindByName(column = "legalMonetaryTotalCbcTaxInclusiveAmount")
    private String legalMonetaryTotalCbcTaxInclusiveAmount;
    @CsvBindByName(column = "legalMonetaryTotalCbcPayableAmount")
    private String legalMonetaryTotalCbcPayableAmount;
    @CsvBindByName(column = "invoiceLineCbcId")
    private String invoiceLineCbcId;
    @CsvBindByName(column = "invoiceLineCbcInvoicedQuantity")
    private String invoiceLineCbcInvoicedQuantity;
    @CsvBindByName(column = "invoiceLineCbcLineExtensionAmount")
    private String invoiceLineCbcLineExtensionAmount;
    @CsvBindByName(column = "currencyId")
    private String currencyId;
    @CsvBindByName(column = "itemCbcName")
    private String itemCbcName;
    @CsvBindByName(column = "invoicePeriodCbcStartDate")
    private String invoicePeriodCbcStartDate;
    @CsvBindByName(column = "invoicePeriodCbcEndDate")
    private String invoicePeriodCbcEndDate;
    @CsvBindByName(column = "descriptionCbcItem")
    private String descriptionCbcItem;
    @CsvBindByName(column = "allowanceChargeCbcChargeIndicator")
    private String allowanceChargeCbcChargeIndicator;
    @CsvBindByName(column = "allowanceChargeCbcAmount")
    private String allowanceChargeCbcAmount;
    @CsvBindByName(column = "allowanceChargeCbcBaseAmount")
    private String allowanceChargeCbcBaseAmount;
    @CsvBindByName(column = "baseAmountCbcCurrencyId")
    private String baseAmountCbcCurrencyId;
    @CsvBindByName(column = "allowanceChargeReason")
    private String allowanceChargeReason;
}
