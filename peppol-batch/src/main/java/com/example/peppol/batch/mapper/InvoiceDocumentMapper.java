package com.example.peppol.batch.mapper;

import com.example.peppol.batch.dto.InvoiceDocument;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import network.oxalis.peppol.ubl2.jaxb.cbc.NoteType;
import network.oxalis.peppol.ubl2.jaxb.cbc.IDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.IssueDateType;
import network.oxalis.peppol.ubl2.jaxb.cbc.DueDateType;
import network.oxalis.peppol.ubl2.jaxb.cbc.InvoiceTypeCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.BuyerReferenceType;
import network.oxalis.peppol.ubl2.jaxb.cbc.StartDateType;
import network.oxalis.peppol.ubl2.jaxb.cbc.EndDateType;
import network.oxalis.peppol.ubl2.jaxb.cbc.EndpointIDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.NameType;
import network.oxalis.peppol.ubl2.jaxb.cbc.StreetNameType;
import network.oxalis.peppol.ubl2.jaxb.cbc.AdditionalStreetNameType;
import network.oxalis.peppol.ubl2.jaxb.cbc.CityNameType;
import network.oxalis.peppol.ubl2.jaxb.cbc.PostalZoneType;
import network.oxalis.peppol.ubl2.jaxb.cbc.CountrySubentityType;
import network.oxalis.peppol.ubl2.jaxb.cbc.LineType;
import network.oxalis.peppol.ubl2.jaxb.cbc.IdentificationCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.CompanyIDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.RegistrationNameType;
import network.oxalis.peppol.ubl2.jaxb.cbc.CompanyLegalFormType;
import network.oxalis.peppol.ubl2.jaxb.cbc.TelephoneType;
import network.oxalis.peppol.ubl2.jaxb.cbc.ElectronicMailType;
import network.oxalis.peppol.ubl2.jaxb.cbc.PaymentMeansCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.PaymentIDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.LineExtensionAmountType;
import network.oxalis.peppol.ubl2.jaxb.cbc.TaxExclusiveAmountType;
import network.oxalis.peppol.ubl2.jaxb.cbc.TaxInclusiveAmountType;
import network.oxalis.peppol.ubl2.jaxb.cbc.PayableAmountType;
import network.oxalis.peppol.ubl2.jaxb.cbc.InvoicedQuantityType;
import network.oxalis.peppol.ubl2.jaxb.cbc.DescriptionType;
import network.oxalis.peppol.ubl2.jaxb.cbc.ChargeIndicatorType;
import network.oxalis.peppol.ubl2.jaxb.cbc.AllowanceChargeReasonType;
import network.oxalis.peppol.ubl2.jaxb.cbc.AmountType;
import network.oxalis.peppol.ubl2.jaxb.cbc.BaseAmountType;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * MapStruct mapper converting {@link InvoiceDocument} instances into
 * UBL {@link InvoiceType} objects.
 */
@Mapper(componentModel = "spring")
public interface InvoiceDocumentMapper {

    @Mappings({
        @Mapping(target = "iD.value", source = "invoiceNumber"),
        @Mapping(target = "issueDate.value", source = "issueDate"),
        @Mapping(target = "dueDate.value", source = "dueDate"),
        @Mapping(target = "invoiceTypeCode.value", source = "invoiceTypeCode"),
        @Mapping(target = "note[0].value", source = "note"),
        @Mapping(target = "buyerReference.value", source = "buyerReference"),
        @Mapping(target = "invoicePeriod.startDate.value", source = "startDate"),
        @Mapping(target = "invoicePeriod.endDate.value", source = "endDate"),
        @Mapping(target = "contractDocumentReference[0].iD.value", source = "contractDocumentReferenceCbcId"),

        // Supplier party mapping
        @Mapping(target = "accountingSupplierParty.party.endpointID.value", source = "supplierEndPoint"),
        @Mapping(target = "accountingSupplierParty.party.partyIdentification[0].iD.value", source = "supplierPartyIdentificationCbcId"),
        @Mapping(target = "accountingSupplierParty.party.partyName[0].name.value", source = "supplierPartyNameCbcName"),
        @Mapping(target = "accountingSupplierParty.party.postalAddress.streetName.value", source = "supplierStreetName"),
        @Mapping(target = "accountingSupplierParty.party.postalAddress.additionalStreetName.value", source = "supplierAdditionalStreetName"),
        @Mapping(target = "accountingSupplierParty.party.postalAddress.cityName.value", source = "supplierCityName"),
        @Mapping(target = "accountingSupplierParty.party.postalAddress.postalZone.value", source = "supplierPostalZone"),
        @Mapping(target = "accountingSupplierParty.party.postalAddress.countrySubentity.value", source = "supplierCountrySubentity"),
        @Mapping(target = "accountingSupplierParty.party.postalAddress.addressLine[0].line.value", source = "supplierAddressLineCbcLine"),
        @Mapping(target = "accountingSupplierParty.party.postalAddress.country.identificationCode.value", source = "supplierCountryCbcIdentificationCode"),
        @Mapping(target = "accountingSupplierParty.party.partyTaxScheme[0].companyID.value", source = "supplierPartyTaxSchemeCompanyId"),
        @Mapping(target = "accountingSupplierParty.party.partyTaxScheme[0].taxScheme.iD.value", source = "supplierPartyTaxSchemeTaxSchemeId"),
        @Mapping(target = "accountingSupplierParty.party.partyLegalEntity[0].registrationName.value", source = "supplierPartyLegalEntityRegistrationName"),
        @Mapping(target = "accountingSupplierParty.party.partyLegalEntity[0].companyID.value", source = "supplierPartyLegalEntityCompanyId"),
        @Mapping(target = "accountingSupplierParty.party.partyLegalEntity[0].companyLegalForm.value", source = "supplierPartyLegalEntityCompanyLegalForm"),

        // Customer party mapping
        @Mapping(target = "accountingCustomerParty.party.endpointID.value", source = "customerEndPoint"),
        @Mapping(target = "accountingCustomerParty.party.partyIdentification[0].iD.value", source = "customerPartyIdentificationCbcId"),
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
        @Mapping(target = "invoiceLine[0].iD.value", source = "invoiceLineCbcId"),
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

    @InheritInverseConfiguration(name = "toInvoice")
    InvoiceDocument fromInvoice(InvoiceType invoice);

    /**
     * Helper creating a {@link NoteType} instance from a String.
     */
    default NoteType toNote(String value) {
        if (value == null) return null;
        NoteType t = new NoteType();
        t.setValue(value);
        return t;
    }

    /**
     * Extracts the first note value from the UBL type or {@code null}.
     */
    default String fromNote(java.util.List<NoteType> notes) {
        if (notes == null || notes.isEmpty()) return null;
        NoteType n = notes.get(0);
        return n == null ? null : n.getValue();
    }

    // -----------------------------------------------------------------
    // Generic converters used by MapStruct for this mapper
    // -----------------------------------------------------------------

    default IDType toID(String value) {
        if (value == null) return null;
        IDType t = new IDType();
        t.setValue(value);
        return t;
    }

    default String fromID(IDType t) {
        return t == null ? null : t.getValue();
    }

    default IssueDateType toIssueDate(String value) {
        if (value == null) return null;
        IssueDateType t = new IssueDateType();
        t.setValue(toXmlDate(value));
        return t;
    }

    default String fromIssueDate(IssueDateType t) {
        return t == null || t.getValue() == null ? null : t.getValue().toString();
    }

    default DueDateType toDueDate(String value) {
        if (value == null) return null;
        DueDateType t = new DueDateType();
        t.setValue(toXmlDate(value));
        return t;
    }

    default String fromDueDate(DueDateType t) {
        return t == null || t.getValue() == null ? null : t.getValue().toString();
    }

    default InvoiceTypeCodeType toInvoiceTypeCode(String value) {
        if (value == null) return null;
        InvoiceTypeCodeType t = new InvoiceTypeCodeType();
        t.setValue(value);
        return t;
    }

    default String fromInvoiceTypeCode(InvoiceTypeCodeType t) {
        return t == null ? null : t.getValue();
    }

    default BuyerReferenceType toBuyerReference(String value) {
        if (value == null) return null;
        BuyerReferenceType t = new BuyerReferenceType();
        t.setValue(value);
        return t;
    }

    default String fromBuyerReference(BuyerReferenceType t) {
        return t == null ? null : t.getValue();
    }

    default StartDateType toStartDate(String value) {
        if (value == null) return null;
        StartDateType t = new StartDateType();
        t.setValue(toXmlDate(value));
        return t;
    }

    default String fromStartDate(StartDateType t) {
        return t == null || t.getValue() == null ? null : t.getValue().toString();
    }

    default EndDateType toEndDate(String value) {
        if (value == null) return null;
        EndDateType t = new EndDateType();
        t.setValue(toXmlDate(value));
        return t;
    }

    default String fromEndDate(EndDateType t) {
        return t == null || t.getValue() == null ? null : t.getValue().toString();
    }

    default EndpointIDType toEndpointID(String value) {
        if (value == null) return null;
        EndpointIDType t = new EndpointIDType();
        t.setValue(value);
        return t;
    }

    default String fromEndpointID(EndpointIDType t) {
        return t == null ? null : t.getValue();
    }

    default NameType toName(String value) {
        if (value == null) return null;
        NameType t = new NameType();
        t.setValue(value);
        return t;
    }

    default String fromName(NameType t) {
        return t == null ? null : t.getValue();
    }

    default StreetNameType toStreetName(String value) {
        if (value == null) return null;
        StreetNameType t = new StreetNameType();
        t.setValue(value);
        return t;
    }

    default String fromStreetName(StreetNameType t) {
        return t == null ? null : t.getValue();
    }

    default AdditionalStreetNameType toAdditionalStreetName(String value) {
        if (value == null) return null;
        AdditionalStreetNameType t = new AdditionalStreetNameType();
        t.setValue(value);
        return t;
    }

    default String fromAdditionalStreetName(AdditionalStreetNameType t) {
        return t == null ? null : t.getValue();
    }

    default CityNameType toCityName(String value) {
        if (value == null) return null;
        CityNameType t = new CityNameType();
        t.setValue(value);
        return t;
    }

    default String fromCityName(CityNameType t) {
        return t == null ? null : t.getValue();
    }

    default PostalZoneType toPostalZone(String value) {
        if (value == null) return null;
        PostalZoneType t = new PostalZoneType();
        t.setValue(value);
        return t;
    }

    default String fromPostalZone(PostalZoneType t) {
        return t == null ? null : t.getValue();
    }

    default CountrySubentityType toCountrySubentity(String value) {
        if (value == null) return null;
        CountrySubentityType t = new CountrySubentityType();
        t.setValue(value);
        return t;
    }

    default String fromCountrySubentity(CountrySubentityType t) {
        return t == null ? null : t.getValue();
    }

    default LineType toLine(String value) {
        if (value == null) return null;
        LineType t = new LineType();
        t.setValue(value);
        return t;
    }

    default String fromLine(LineType t) {
        return t == null ? null : t.getValue();
    }

    default IdentificationCodeType toIdentificationCode(String value) {
        if (value == null) return null;
        IdentificationCodeType t = new IdentificationCodeType();
        t.setValue(value);
        return t;
    }

    default String fromIdentificationCode(IdentificationCodeType t) {
        return t == null ? null : t.getValue();
    }

    default CompanyIDType toCompanyID(String value) {
        if (value == null) return null;
        CompanyIDType t = new CompanyIDType();
        t.setValue(value);
        return t;
    }

    default String fromCompanyID(CompanyIDType t) {
        return t == null ? null : t.getValue();
    }

    default RegistrationNameType toRegistrationName(String value) {
        if (value == null) return null;
        RegistrationNameType t = new RegistrationNameType();
        t.setValue(value);
        return t;
    }

    default String fromRegistrationName(RegistrationNameType t) {
        return t == null ? null : t.getValue();
    }

    default CompanyLegalFormType toCompanyLegalForm(String value) {
        if (value == null) return null;
        CompanyLegalFormType t = new CompanyLegalFormType();
        t.setValue(value);
        return t;
    }

    default String fromCompanyLegalForm(CompanyLegalFormType t) {
        return t == null ? null : t.getValue();
    }

    default TelephoneType toTelephone(String value) {
        if (value == null) return null;
        TelephoneType t = new TelephoneType();
        t.setValue(value);
        return t;
    }

    default String fromTelephone(TelephoneType t) {
        return t == null ? null : t.getValue();
    }

    default ElectronicMailType toElectronicMail(String value) {
        if (value == null) return null;
        ElectronicMailType t = new ElectronicMailType();
        t.setValue(value);
        return t;
    }

    default String fromElectronicMail(ElectronicMailType t) {
        return t == null ? null : t.getValue();
    }

    default PaymentMeansCodeType toPaymentMeansCode(String value) {
        if (value == null) return null;
        PaymentMeansCodeType t = new PaymentMeansCodeType();
        t.setValue(value);
        return t;
    }

    default String fromPaymentMeansCode(PaymentMeansCodeType t) {
        return t == null ? null : t.getValue();
    }

    default PaymentIDType toPaymentID(String value) {
        if (value == null) return null;
        PaymentIDType t = new PaymentIDType();
        t.setValue(value);
        return t;
    }

    default String fromPaymentID(PaymentIDType t) {
        return t == null ? null : t.getValue();
    }

    default LineExtensionAmountType toLineExtensionAmount(String value) {
        if (value == null) return null;
        LineExtensionAmountType t = new LineExtensionAmountType();
        t.setValue(new java.math.BigDecimal(value));
        return t;
    }

    default String fromLineExtensionAmount(LineExtensionAmountType t) {
        return t == null || t.getValue() == null ? null : t.getValue().toString();
    }

    default TaxExclusiveAmountType toTaxExclusiveAmount(String value) {
        if (value == null) return null;
        TaxExclusiveAmountType t = new TaxExclusiveAmountType();
        t.setValue(new java.math.BigDecimal(value));
        return t;
    }

    default String fromTaxExclusiveAmount(TaxExclusiveAmountType t) {
        return t == null || t.getValue() == null ? null : t.getValue().toString();
    }

    default TaxInclusiveAmountType toTaxInclusiveAmount(String value) {
        if (value == null) return null;
        TaxInclusiveAmountType t = new TaxInclusiveAmountType();
        t.setValue(new java.math.BigDecimal(value));
        return t;
    }

    default String fromTaxInclusiveAmount(TaxInclusiveAmountType t) {
        return t == null || t.getValue() == null ? null : t.getValue().toString();
    }

    default PayableAmountType toPayableAmount(String value) {
        if (value == null) return null;
        PayableAmountType t = new PayableAmountType();
        t.setValue(new java.math.BigDecimal(value));
        return t;
    }

    default String fromPayableAmount(PayableAmountType t) {
        return t == null || t.getValue() == null ? null : t.getValue().toString();
    }

    default InvoicedQuantityType toInvoicedQuantity(String value) {
        if (value == null) return null;
        InvoicedQuantityType t = new InvoicedQuantityType();
        t.setValue(new java.math.BigDecimal(value));
        return t;
    }

    default String fromInvoicedQuantity(InvoicedQuantityType t) {
        return t == null || t.getValue() == null ? null : t.getValue().toString();
    }

    default DescriptionType toDescription(String value) {
        if (value == null) return null;
        DescriptionType t = new DescriptionType();
        t.setValue(value);
        return t;
    }

    default String fromDescription(DescriptionType t) {
        return t == null ? null : t.getValue();
    }

    default ChargeIndicatorType toChargeIndicator(String value) {
        if (value == null) return null;
        ChargeIndicatorType t = new ChargeIndicatorType();
        t.setValue(Boolean.parseBoolean(value));
        return t;
    }

    default String fromChargeIndicator(ChargeIndicatorType t) {
        return t == null || t.getValue() == null ? null : t.getValue().toString();
    }

    default AllowanceChargeReasonType toAllowanceChargeReason(String value) {
        if (value == null) return null;
        AllowanceChargeReasonType t = new AllowanceChargeReasonType();
        t.setValue(value);
        return t;
    }

    default String fromAllowanceChargeReason(AllowanceChargeReasonType t) {
        return t == null ? null : t.getValue();
    }

    default AmountType toAmount(String value) {
        if (value == null) return null;
        AmountType t = new AmountType();
        t.setValue(new java.math.BigDecimal(value));
        return t;
    }

    default String fromAmount(AmountType t) {
        return t == null || t.getValue() == null ? null : t.getValue().toString();
    }

    default BaseAmountType toBaseAmount(String value) {
        if (value == null) return null;
        BaseAmountType t = new BaseAmountType();
        t.setValue(new java.math.BigDecimal(value));
        return t;
    }

    default String fromBaseAmount(BaseAmountType t) {
        return t == null || t.getValue() == null ? null : t.getValue().toString();
    }

    private XMLGregorianCalendar toXmlDate(String value) {
        java.time.LocalDate local = java.time.LocalDate.parse(value);
        try {
            return DatatypeFactory.newInstance()
                    .newXMLGregorianCalendarDate(
                            local.getYear(),
                            local.getMonthValue(),
                            local.getDayOfMonth(),
                            DatatypeConstants.FIELD_UNDEFINED);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
