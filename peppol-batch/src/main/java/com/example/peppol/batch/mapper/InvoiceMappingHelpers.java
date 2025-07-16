package com.example.peppol.batch.mapper;

import network.oxalis.peppol.ubl2.jaxb.cbc.*;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Helper methods used by the invoice mappers for converting between
 * primitive types and UBL specialised types.
 */
public interface InvoiceMappingHelpers {
    default NoteType toNote(String value) {
        if (value == null) return null;
        NoteType t = new NoteType();
        t.setValue(value);
        return t;
    }

    default String fromNote(java.util.List<NoteType> notes) {
        if (notes == null || notes.isEmpty()) return null;
        NoteType n = notes.get(0);
        return n == null ? null : n.getValue();
    }

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
