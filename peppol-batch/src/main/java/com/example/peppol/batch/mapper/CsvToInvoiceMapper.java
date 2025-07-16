package com.example.peppol.batch.mapper;

import com.example.peppol.batch.dto.InvoiceDocument;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper converting a flat {@link InvoiceDocument} loaded from CSV into
 * the UBL {@link InvoiceType} representation.
 */
@Mapper(componentModel = "spring")
public interface CsvToInvoiceMapper extends InvoiceMappingHelpers {

    @Mappings({
        @Mapping(target = "iD.value", source = "invoiceNumber"),
        @Mapping(target = "issueDate.value", source = "issueDate"),
        @Mapping(target = "dueDate.value", source = "dueDate"),
        @Mapping(target = "invoiceTypeCode.value", source = "invoiceTypeCode"),
        @Mapping(target = "note", expression = "java(toNoteList(doc.getNote()))"),
        @Mapping(target = "buyerReference.value", source = "buyerReference"),
        @Mapping(target = "invoicePeriod[0].startDate.value", source = "startDate"),
        @Mapping(target = "invoicePeriod[0].endDate.value", source = "endDate"),
        @Mapping(target = "contractDocumentReference[0].ID.value", source = "contractDocumentReferenceCbcId"),

        // Supplier party mapping
        @Mapping(target = "accountingSupplierParty.party.endpointID.value", source = "supplierEndPoint"),
        @Mapping(target = "accountingSupplierParty.party.partyIdentification[0].ID.value", source = "supplierPartyIdentificationCbcId"),
        @Mapping(target = "accountingSupplierParty.party.partyName[0].name.value", source = "supplierPartyNameCbcName"),
        @Mapping(target = "accountingSupplierParty.party.postalAddress.streetName.value", source = "supplierStreetName"),
        @Mapping(target = "accountingSupplierParty.party.postalAddress.additionalStreetName.value", source = "supplierAdditionalStreetName"),
        @Mapping(target = "accountingSupplierParty.party.postalAddress.cityName.value", source = "supplierCityName"),
        @Mapping(target = "accountingSupplierParty.party.postalAddress.postalZone.value", source = "supplierPostalZone"),
        @Mapping(target = "accountingSupplierParty.party.postalAddress.countrySubentity.value", source = "supplierCountrySubentity"),
        @Mapping(target = "accountingSupplierParty.party.postalAddress.addressLine[0].line.value", source = "supplierAddressLineCbcLine"),
        @Mapping(target = "accountingSupplierParty.party.postalAddress.country.identificationCode.value", source = "supplierCountryCbcIdentificationCode"),
        @Mapping(target = "accountingSupplierParty.party.partyTaxScheme[0].companyID.value", source = "supplierPartyTaxSchemeCompanyId"),
        @Mapping(target = "accountingSupplierParty.party.partyTaxScheme[0].taxScheme.ID.value", source = "supplierPartyTaxSchemeTaxSchemeId"),
        @Mapping(target = "accountingSupplierParty.party.partyLegalEntity[0].registrationName.value", source = "supplierPartyLegalEntityRegistrationName"),
        @Mapping(target = "accountingSupplierParty.party.partyLegalEntity[0].companyID.value", source = "supplierPartyLegalEntityCompanyId"),
        @Mapping(target = "accountingSupplierParty.party.partyLegalEntity[0].companyLegalForm.value", source = "supplierPartyLegalEntityCompanyLegalForm"),

        // Customer party mapping
        @Mapping(target = "accountingCustomerParty.party.endpointID.value", source = "customerEndPoint"),
        @Mapping(target = "accountingCustomerParty.party.partyIdentification[0].ID.value", source = "customerPartyIdentificationCbcId"),
        @Mapping(target = "accountingCustomerParty.party.partyName[0].name.value", source = "customerPartyNameCbcName"),
        @Mapping(target = "accountingCustomerParty.party.postalAddress.streetName.value", source = "customerStreetName"),
        @Mapping(target = "accountingCustomerParty.party.postalAddress.additionalStreetName.value", source = "customerAdditionalStreetName"),
        @Mapping(target = "accountingCustomerParty.party.postalAddress.cityName.value", source = "customerCityName"),
        @Mapping(target = "accountingCustomerParty.party.postalAddress.postalZone.value", source = "customerPostalZone"),
        @Mapping(target = "accountingCustomerParty.party.postalAddress.countrySubentity.value", source = "customerCountrySubentity"),
        @Mapping(target = "accountingCustomerParty.party.postalAddress.addressLine[0].line.value", source = "customerAddressLineCbcLine"),
        @Mapping(target = "accountingCustomerParty.party.postalAddress.country.identificationCode.value", source = "customerCountryCbcIdentificationCode"),
        @Mapping(target = "accountingCustomerParty.party.partyLegalEntity[0].registrationName.value", source = "customerPartyLegalEntityRegistrationName"),
        @Mapping(target = "accountingCustomerParty.party.partyLegalEntity[0].companyID.value", source = "customerPartyLegalEntityCompanyId"),
        @Mapping(target = "accountingCustomerParty.party.contact.name.value", source = "customerContactName"),
        @Mapping(target = "accountingCustomerParty.party.contact.telephone.value", source = "customerContactTelephone"),
        @Mapping(target = "accountingCustomerParty.party.contact.electronicMail.value", source = "customerContactElectronicMail"),

        // Payment / monetary totals
        @Mapping(target = "paymentMeans[0].paymentMeansCode.value", source = "paymentMeans"),
        @Mapping(target = "paymentMeans[0].paymentID[0].value", source = "paymentMeansCbcPaymentId"),
        @Mapping(target = "legalMonetaryTotal.lineExtensionAmount.value", source = "legalMonetaryTotalCbcLineExtensionAmount"),
        @Mapping(target = "legalMonetaryTotal.taxExclusiveAmount.value", source = "legalMonetaryTotalCbcTaxExclusiveAmount"),
        @Mapping(target = "legalMonetaryTotal.taxInclusiveAmount.value", source = "legalMonetaryTotalCbcTaxInclusiveAmount"),
        @Mapping(target = "legalMonetaryTotal.payableAmount.value", source = "legalMonetaryTotalCbcPayableAmount"),

        // Invoice line (flat example)
        @Mapping(target = "invoiceLine[0].ID.value", source = "invoiceLineCbcId"),
        @Mapping(target = "invoiceLine[0].invoicedQuantity.value", source = "invoiceLineCbcInvoicedQuantity"),
        @Mapping(target = "invoiceLine[0].lineExtensionAmount.value", source = "invoiceLineCbcLineExtensionAmount"),
        @Mapping(target = "invoiceLine[0].lineExtensionAmount.currencyID", source = "currencyId"),
        @Mapping(target = "invoiceLine[0].item.name.value", source = "itemCbcName"),
        @Mapping(target = "invoiceLine[0].invoicePeriod.startDate.value", source = "invoicePeriodCbcStartDate"),
        @Mapping(target = "invoiceLine[0].invoicePeriod.endDate.value", source = "invoicePeriodCbcEndDate"),
        @Mapping(target = "invoiceLine[0].item.description[0].value", source = "descriptionCbcItem"),

        // Allowance/charge
        @Mapping(target = "allowanceCharge[0].chargeIndicator.value", source = "allowanceChargeCbcChargeIndicator"),
        @Mapping(target = "allowanceCharge[0].allowanceChargeReason.value", source = "allowanceChargeCbcAllowanceChargeReason"),
        @Mapping(target = "allowanceCharge[0].amount.value", source = "allowanceChargeCbcAmount"),
        @Mapping(target = "allowanceCharge[0].baseAmount.currencyID", source = "baseAmountCbcCurrencyId"),
        @Mapping(target = "allowanceCharge[0].baseAmount.value", source = "allowanceChargeCbcBaseAmount")
    })
    InvoiceType toInvoice(InvoiceDocument doc);
}
