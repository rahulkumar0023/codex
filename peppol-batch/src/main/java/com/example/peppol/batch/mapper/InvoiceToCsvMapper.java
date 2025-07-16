package com.example.peppol.batch.mapper;

import com.example.peppol.batch.dto.InvoiceDocument;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper converting a UBL {@link InvoiceType} back into the flat
 * {@link InvoiceDocument} for CSV output.
 */
@Mapper(componentModel = "spring")
public interface InvoiceToCsvMapper extends InvoiceMappingHelpers {

    @Mappings({
        @Mapping(target = "invoiceNumber", source = "ID.value"),
        @Mapping(target = "issueDate", source = "issueDate.value"),
        @Mapping(target = "dueDate", source = "dueDate.value"),
        @Mapping(target = "invoiceTypeCode", source = "invoiceTypeCode.value"),
        @Mapping(target = "note", source = "note"),
        @Mapping(target = "buyerReference", source = "buyerReference.value"),
        @Mapping(target = "startDate", source = "invoicePeriod[0].startDate.value"),
        @Mapping(target = "endDate", source = "invoicePeriod[0].endDate.value"),
        @Mapping(target = "contractDocumentReferenceCbcId", source = "contractDocumentReference[0].ID.value"),

        // Supplier
        @Mapping(target = "supplierEndPoint", source = "accountingSupplierParty.party.endpointID.value"),
        @Mapping(target = "supplierPartyIdentificationCbcId", source = "accountingSupplierParty.party.partyIdentification[0].ID.value"),
        @Mapping(target = "supplierPartyNameCbcName", source = "accountingSupplierParty.party.partyName[0].name.value"),
        @Mapping(target = "supplierStreetName", source = "accountingSupplierParty.party.postalAddress.streetName.value"),
        @Mapping(target = "supplierAdditionalStreetName", source = "accountingSupplierParty.party.postalAddress.additionalStreetName.value"),
        @Mapping(target = "supplierCityName", source = "accountingSupplierParty.party.postalAddress.cityName.value"),
        @Mapping(target = "supplierPostalZone", source = "accountingSupplierParty.party.postalAddress.postalZone.value"),
        @Mapping(target = "supplierCountrySubentity", source = "accountingSupplierParty.party.postalAddress.countrySubentity.value"),
        @Mapping(target = "supplierAddressLineCbcLine", source = "accountingSupplierParty.party.postalAddress.addressLine[0].line.value"),
        @Mapping(target = "supplierCountryCbcIdentificationCode", source = "accountingSupplierParty.party.postalAddress.country.identificationCode.value"),
        @Mapping(target = "supplierPartyTaxSchemeCompanyId", source = "accountingSupplierParty.party.partyTaxScheme[0].companyID.value"),
        @Mapping(target = "supplierPartyTaxSchemeTaxSchemeId", source = "accountingSupplierParty.party.partyTaxScheme[0].taxScheme.ID.value"),
        @Mapping(target = "supplierPartyLegalEntityRegistrationName", source = "accountingSupplierParty.party.partyLegalEntity[0].registrationName.value"),
        @Mapping(target = "supplierPartyLegalEntityCompanyId", source = "accountingSupplierParty.party.partyLegalEntity[0].companyID.value"),
        @Mapping(target = "supplierPartyLegalEntityCompanyLegalForm", source = "accountingSupplierParty.party.partyLegalEntity[0].companyLegalForm.value"),

        // Customer
        @Mapping(target = "customerEndPoint", source = "accountingCustomerParty.party.endpointID.value"),
        @Mapping(target = "customerPartyIdentificationCbcId", source = "accountingCustomerParty.party.partyIdentification[0].ID.value"),
        @Mapping(target = "customerPartyNameCbcName", source = "accountingCustomerParty.party.partyName[0].name.value"),
        @Mapping(target = "customerStreetName", source = "accountingCustomerParty.party.postalAddress.streetName.value"),
        @Mapping(target = "customerAdditionalStreetName", source = "accountingCustomerParty.party.postalAddress.additionalStreetName.value"),
        @Mapping(target = "customerCityName", source = "accountingCustomerParty.party.postalAddress.cityName.value"),
        @Mapping(target = "customerPostalZone", source = "accountingCustomerParty.party.postalAddress.postalZone.value"),
        @Mapping(target = "customerCountrySubentity", source = "accountingCustomerParty.party.postalAddress.countrySubentity.value"),
        @Mapping(target = "customerAddressLineCbcLine", source = "accountingCustomerParty.party.postalAddress.addressLine[0].line.value"),
        @Mapping(target = "customerCountryCbcIdentificationCode", source = "accountingCustomerParty.party.postalAddress.country.identificationCode.value"),
        @Mapping(target = "customerPartyLegalEntityRegistrationName", source = "accountingCustomerParty.party.partyLegalEntity[0].registrationName.value"),
        @Mapping(target = "customerPartyLegalEntityCompanyId", source = "accountingCustomerParty.party.partyLegalEntity[0].companyID.value"),
        @Mapping(target = "customerContactName", source = "accountingCustomerParty.party.contact.name.value"),
        @Mapping(target = "customerContactTelephone", source = "accountingCustomerParty.party.contact.telephone.value"),
        @Mapping(target = "customerContactElectronicMail", source = "accountingCustomerParty.party.contact.electronicMail.value"),

        // Monetary totals
        @Mapping(target = "paymentMeans", source = "paymentMeans[0].paymentMeansCode.value"),
        @Mapping(target = "paymentMeansCbcPaymentId", source = "paymentMeans[0].paymentID[0].value"),
        @Mapping(target = "legalMonetaryTotalCbcLineExtensionAmount", source = "legalMonetaryTotal.lineExtensionAmount.value"),
        @Mapping(target = "legalMonetaryTotalCbcTaxExclusiveAmount", source = "legalMonetaryTotal.taxExclusiveAmount.value"),
        @Mapping(target = "legalMonetaryTotalCbcTaxInclusiveAmount", source = "legalMonetaryTotal.taxInclusiveAmount.value"),
        @Mapping(target = "legalMonetaryTotalCbcPayableAmount", source = "legalMonetaryTotal.payableAmount.value"),

        // Line
        @Mapping(target = "invoiceLineCbcId", source = "invoiceLine[0].ID.value"),
        @Mapping(target = "invoiceLineCbcInvoicedQuantity", source = "invoiceLine[0].invoicedQuantity.value"),
        @Mapping(target = "invoiceLineCbcLineExtensionAmount", source = "invoiceLine[0].lineExtensionAmount.value"),
        @Mapping(target = "currencyId", source = "invoiceLine[0].lineExtensionAmount.currencyID"),
        @Mapping(target = "itemCbcName", source = "invoiceLine[0].item.name.value"),
        @Mapping(target = "invoicePeriodCbcStartDate", source = "invoiceLine[0].invoicePeriod.startDate.value"),
        @Mapping(target = "invoicePeriodCbcEndDate", source = "invoiceLine[0].invoicePeriod.endDate.value"),
        @Mapping(target = "descriptionCbcItem", source = "invoiceLine[0].item.description[0].value"),

        // Allowance/charge
        @Mapping(target = "allowanceChargeCbcChargeIndicator", source = "allowanceCharge[0].chargeIndicator.value"),
        @Mapping(target = "allowanceChargeCbcAllowanceChargeReason", source = "allowanceCharge[0].allowanceChargeReason.value"),
        @Mapping(target = "allowanceChargeCbcAmount", source = "allowanceCharge[0].amount.value"),
        @Mapping(target = "baseAmountCbcCurrencyId", source = "allowanceCharge[0].baseAmount.currencyID"),
        @Mapping(target = "allowanceChargeCbcBaseAmount", source = "allowanceCharge[0].baseAmount.value")
    })
    InvoiceDocument fromInvoice(InvoiceType invoice);
}
