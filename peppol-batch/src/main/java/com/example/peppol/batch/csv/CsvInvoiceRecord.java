package com.example.peppol.batch.csv;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Strongly typed representation of a CSV invoice row.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsvInvoiceRecord {
//    @CsvBindByName(column = "CustomizationID")
//    private String customizationID;
//    @CsvBindByName(column = "ProfileID")
//    private String profileID;
//    @CsvBindByName(column = "ID")
//    private String id;
    @CsvBindByName(column = "IssueDate")
    private String issueDate;
    @CsvBindByName(column = "DueDate")
    private String dueDate;
    @CsvBindByName(column = "InvoiceTypeCode")
    private String invoiceTypeCode;
    @CsvBindByName(column = "Note")
    private String note;
    @CsvBindByName(column = "TaxPointDate")
    private String taxPointDate;
    @CsvBindByName(column = "DocumentCurrencyCode")
    private String documentCurrencyCode;
    @CsvBindByName(column = "TaxCurrencyCode")
    private String taxCurrencyCode;
    // additional fields from the extensive header
    @CsvBindByName(column = "AccountingCost")
    private String accountingCost;
    @CsvBindByName(column = "BuyerReference")
    private String buyerReference;
    @CsvBindByName(column = "InvoicePeriod_StartDate")
    private String invoicePeriodStartDate;
    @CsvBindByName(column = "InvoicePeriod_EndDate")
    private String invoicePeriodEndDate;
    @CsvBindByName(column = "ContractDocumentReference_ID")
    private String contractDocumentReferenceID;
    @CsvBindByName(column = "AdditionalDocumentReference1_ID")
    private String additionalDocumentReference1ID;
    @CsvBindByName(column = "AdditionalDocumentReference1_DocumentTypeCode")
    private String additionalDocumentReference1DocumentTypeCode;
    @CsvBindByName(column = "AdditionalDocumentReference2_ID")
    private String additionalDocumentReference2ID;
    @CsvBindByName(column = "AdditionalDocumentReference2_DocumentDescription")
    private String additionalDocumentReference2DocumentDescription;
    @CsvBindByName(column = "AdditionalDocumentReference2_Attachment_URI")
    private String additionalDocumentReference2AttachmentURI;
    @CsvBindByName(column = "AccountingSupplierParty_EndpointID")
    private String accountingSupplierPartyEndpointID;
    @CsvBindByName(column = "AccountingSupplierParty_PartyIdentification_ID")
    private String accountingSupplierPartyPartyIdentificationID;
    @CsvBindByName(column = "AccountingSupplierParty_PartyName_Name")
    private String accountingSupplierPartyPartyNameName;
    @CsvBindByName(column = "AccountingSupplierParty_StreetName")
    private String accountingSupplierPartyStreetName;
    @CsvBindByName(column = "AccountingSupplierParty_AdditionalStreetName")
    private String accountingSupplierPartyAdditionalStreetName;
    @CsvBindByName(column = "AccountingSupplierParty_CityName")
    private String accountingSupplierPartyCityName;
    @CsvBindByName(column = "AccountingSupplierParty_PostalZone")
    private String accountingSupplierPartyPostalZone;
    @CsvBindByName(column = "AccountingSupplierParty_Country_IdentificationCode")
    private String accountingSupplierPartyCountryIdentificationCode;
    @CsvBindByName(column = "AccountingSupplierParty_PartyTaxScheme_CompanyID")
    private String accountingSupplierPartyPartyTaxSchemeCompanyID;
    @CsvBindByName(column = "AccountingSupplierParty_PartyTaxScheme_TaxScheme_ID")
    private String accountingSupplierPartyPartyTaxSchemeTaxSchemeID;
    @CsvBindByName(column = "AccountingSupplierParty_PartyLegalEntity_RegistrationName")
    private String accountingSupplierPartyPartyLegalEntityRegistrationName;
    @CsvBindByName(column = "AccountingSupplierParty_PartyLegalEntity_CompanyID")
    private String accountingSupplierPartyPartyLegalEntityCompanyID;
    @CsvBindByName(column = "AccountingSupplierParty_PartyLegalEntity_CompanyLegalForm")
    private String accountingSupplierPartyPartyLegalEntityCompanyLegalForm;
    @CsvBindByName(column = "AccountingCustomerParty_EndpointID")
    private String accountingCustomerPartyEndpointID;
    @CsvBindByName(column = "AccountingCustomerParty_PartyIdentification_ID")
    private String accountingCustomerPartyPartyIdentificationID;
    @CsvBindByName(column = "AccountingCustomerParty_PartyName_Name")
    private String accountingCustomerPartyPartyNameName;
    @CsvBindByName(column = "AccountingCustomerParty_StreetName")
    private String accountingCustomerPartyStreetName;
    @CsvBindByName(column = "AccountingCustomerParty_AdditionalStreetName")
    private String accountingCustomerPartyAdditionalStreetName;
    @CsvBindByName(column = "AccountingCustomerParty_CityName")
    private String accountingCustomerPartyCityName;
    @CsvBindByName(column = "AccountingCustomerParty_PostalZone")
    private String accountingCustomerPartyPostalZone;
    @CsvBindByName(column = "AccountingCustomerParty_CountrySubentity")
    private String accountingCustomerPartyCountrySubentity;
    @CsvBindByName(column = "AccountingCustomerParty_Country_IdentificationCode")
    private String accountingCustomerPartyCountryIdentificationCode;
    @CsvBindByName(column = "AccountingCustomerParty_PartyTaxScheme_CompanyID")
    private String accountingCustomerPartyPartyTaxSchemeCompanyID;
    @CsvBindByName(column = "AccountingCustomerParty_PartyTaxScheme_TaxScheme_ID")
    private String accountingCustomerPartyPartyTaxSchemeTaxSchemeID;
    @CsvBindByName(column = "AccountingCustomerParty_PartyLegalEntity_RegistrationName")
    private String accountingCustomerPartyPartyLegalEntityRegistrationName;
    @CsvBindByName(column = "AccountingCustomerParty_PartyLegalEntity_CompanyID")
    private String accountingCustomerPartyPartyLegalEntityCompanyID;
    @CsvBindByName(column = "AccountingCustomerParty_Contact_Name")
    private String accountingCustomerPartyContactName;
    @CsvBindByName(column = "AccountingCustomerParty_Contact_Telephone")
    private String accountingCustomerPartyContactTelephone;
    @CsvBindByName(column = "AccountingCustomerParty_Contact_ElectronicMail")
    private String accountingCustomerPartyContactElectronicMail;
    @CsvBindByName(column = "Delivery_ActualDeliveryDate")
    private String deliveryActualDeliveryDate;
    @CsvBindByName(column = "Delivery_DeliveryLocation_ID")
    private String deliveryDeliveryLocationID;
    @CsvBindByName(column = "Delivery_DeliveryLocation_StreetName")
    private String deliveryDeliveryLocationStreetName;
    @CsvBindByName(column = "Delivery_DeliveryLocation_AdditionalStreetName")
    private String deliveryDeliveryLocationAdditionalStreetName;
    @CsvBindByName(column = "Delivery_DeliveryLocation_CityName")
    private String deliveryDeliveryLocationCityName;
    @CsvBindByName(column = "Delivery_DeliveryLocation_PostalZone")
    private String deliveryDeliveryLocationPostalZone;
    @CsvBindByName(column = "Delivery_DeliveryLocation_CountrySubentity")
    private String deliveryDeliveryLocationCountrySubentity;
    @CsvBindByName(column = "Delivery_DeliveryLocation_AddressLine")
    private String deliveryDeliveryLocationAddressLine;
    @CsvBindByName(column = "Delivery_DeliveryLocation_Country_IdentificationCode")
    private String deliveryDeliveryLocationCountryIdentificationCode;
    @CsvBindByName(column = "Delivery_DeliveryParty_Name")
    private String deliveryDeliveryPartyName;
    /**
     * Flat representation of the payment means code used by some CSV files.
     * When present, this value maps to the first PaymentMeans element in the
     * resulting UBL document.
     */
    @CsvBindByName(column = "paymentMeans")
    private String paymentMeans;
    @CsvBindByName(column = "PaymentMeans_PaymentMeansCode")
    private String paymentMeansPaymentMeansCode;
    @CsvBindByName(column = "PaymentMeans_PaymentID")
    private String paymentMeansPaymentID;
    @CsvBindByName(column = "PaymentMeans_PayeeFinancialAccount_ID")
    private String paymentMeansPayeeFinancialAccountID;
    @CsvBindByName(column = "PaymentMeans_PayeeFinancialAccount_Name")
    private String paymentMeansPayeeFinancialAccountName;
    @CsvBindByName(column = "PaymentMeans_PayeeFinancialAccount_BranchID")
    private String paymentMeansPayeeFinancialAccountBranchID;
    @CsvBindByName(column = "PaymentTerms_Note")
    private String paymentTermsNote;
}
