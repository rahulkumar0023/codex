package com.example.peppol.batch.csv;

import org.mapstruct.*;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import network.oxalis.peppol.ubl2.jaxb.cbc.CustomizationIDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.DocumentCurrencyCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.DueDateType;
import network.oxalis.peppol.ubl2.jaxb.cbc.CopyIndicatorType;
import network.oxalis.peppol.ubl2.jaxb.cbc.IDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.InvoiceTypeCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.IssueDateType;
import network.oxalis.peppol.ubl2.jaxb.cbc.IssueTimeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.NoteType;
import network.oxalis.peppol.ubl2.jaxb.cbc.ProfileIDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.ProfileExecutionIDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.TaxCurrencyCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.TaxPointDateType;
import network.oxalis.peppol.ubl2.jaxb.cbc.UBLVersionIDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.UUIDType;
import network.oxalis.peppol.ubl2.jaxb.cac.InvoiceLineType;
import network.oxalis.peppol.ubl2.jaxb.cac.ItemPropertyType;
import network.oxalis.peppol.ubl2.jaxb.cac.ItemType;
import network.oxalis.peppol.ubl2.jaxb.cbc.NameType;
import network.oxalis.peppol.ubl2.jaxb.cbc.ValueType;
import network.oxalis.peppol.ubl2.jaxb.ecdt.UBLExtensionsType;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper converting {@link CsvInvoiceRecord} instances to
 * {@link InvoiceType}.
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface CsvInvoiceMapper {
    CsvInvoiceMapper INSTANCE = Mappers.getMapper(CsvInvoiceMapper.class);

    @Mapping(target = "ID", expression = "java(toID(item.getId()))")
    @Mapping(target = "customizationID", expression = "java(toCustomizationID(item.getCustomizationId()))")
    @Mapping(target = "profileID", expression = "java(toProfileID(item.getProfileId()))")
    @Mapping(target = "invoiceTypeCode", expression = "java(toInvoiceTypeCode(item.getInvoiceTypeCode()))")
    @Mapping(target = "issueDate", expression = "java(toIssueDate(item.getIssueDate()))")
    @Mapping(target = "dueDate", expression = "java(toDueDate(item.getDueDate()))")
    @Mapping(target = "taxPointDate", expression = "java(toTaxPointDate(item.getTaxPointDate()))")
    @Mapping(target = "documentCurrencyCode", expression = "java(toDocumentCurrencyCode(item.getDocumentCurrencyCode()))")
    @Mapping(target = "taxCurrencyCode", expression = "java(toTaxCurrencyCode(item.getTaxCurrencyCode()))")
    @Mapping(target = "UBLVersionID", expression = "java(toUBLVersionID(item.getUblVersionId()))")
    @Mapping(target = "profileExecutionID", expression = "java(toProfileExecutionID(item.getProfileExecutionId()))")
    @Mapping(target = "copyIndicator", expression = "java(toCopyIndicator(item.getCopyIndicator()))")
    @Mapping(target = "UUID", expression = "java(toUUID(item.getUuid()))")
    @Mapping(target = "issueTime", expression = "java(toIssueTime(item.getIssueTime()))")
    @Mapping(target = "UBLExtensions", expression = "java(emptyExtensions())")
    @Mapping(target = "note", expression = "java(item.getNote() == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(java.util.Collections.singletonList(toNote(item.getNote()))))")
    InvoiceType toInvoice(CsvInvoiceRecord item);

    @AfterMapping
    default void mapChassisProperty(CsvInvoiceRecord item, @MappingTarget InvoiceType invoice) {
        String val = item.getChassisValue();
        if (val == null) {
            return;
        }
        InvoiceLineType line = new InvoiceLineType();
        ItemType itemType = new ItemType();
        ItemPropertyType prop = new ItemPropertyType();
        NameType name = new NameType();
        name.setValue("chassis");
        prop.setName(name);
        ValueType value = new ValueType();
        value.setValue(val);
        prop.setValue(value);
        itemType.getAdditionalItemProperty().add(prop);
        line.setItem(itemType);
        invoice.getInvoiceLine().add(line);
    }

    default IDType toID(String value) {
        if (value == null) return null;
        IDType t = new IDType();
        t.setValue(value);
        return t;
    }

    default CustomizationIDType toCustomizationID(String value) {
        if (value == null) return null;
        CustomizationIDType t = new CustomizationIDType();
        t.setValue(value);
        return t;
    }

    default ProfileIDType toProfileID(String value) {
        if (value == null) return null;
        ProfileIDType t = new ProfileIDType();
        t.setValue(value);
        return t;
    }

    default InvoiceTypeCodeType toInvoiceTypeCode(String value) {
        if (value == null) return null;
        InvoiceTypeCodeType t = new InvoiceTypeCodeType();
        t.setValue(value);
        return t;
    }

    default NoteType toNote(String value) {
        if (value == null) return null;
        NoteType t = new NoteType();
        t.setValue(value);
        return t;
    }

    default IssueDateType toIssueDate(String value) {
        if (value == null) return null;
        IssueDateType t = new IssueDateType();
        t.setValue(toXmlDate(value));
        return t;
    }

    default DueDateType toDueDate(String value) {
        if (value == null) return null;
        DueDateType t = new DueDateType();
        t.setValue(toXmlDate(value));
        return t;
    }

    default TaxPointDateType toTaxPointDate(String value) {
        if (value == null) return null;
        TaxPointDateType t = new TaxPointDateType();
        t.setValue(toXmlDate(value));
        return t;
    }

    default DocumentCurrencyCodeType toDocumentCurrencyCode(String value) {
        if (value == null) return null;
        DocumentCurrencyCodeType t = new DocumentCurrencyCodeType();
        t.setValue(value);
        return t;
    }

    default TaxCurrencyCodeType toTaxCurrencyCode(String value) {
        if (value == null) return null;
        TaxCurrencyCodeType t = new TaxCurrencyCodeType();
        t.setValue(value);
        return t;
    }

    default UBLVersionIDType toUBLVersionID(String value) {
        if (value == null) return null;
        UBLVersionIDType t = new UBLVersionIDType();
        t.setValue(value);
        return t;
    }

    default ProfileExecutionIDType toProfileExecutionID(String value) {
        if (value == null) return null;
        ProfileExecutionIDType t = new ProfileExecutionIDType();
        t.setValue(value);
        return t;
    }

    default CopyIndicatorType toCopyIndicator(String value) {
        if (value == null) return null;
        CopyIndicatorType t = new CopyIndicatorType();
        t.setValue(Boolean.parseBoolean(value));
        return t;
    }

    default UUIDType toUUID(String value) {
        if (value == null) return null;
        UUIDType t = new UUIDType();
        t.setValue(value);
        return t;
    }

    default IssueTimeType toIssueTime(String value) {
        if (value == null) return null;
        IssueTimeType t = new IssueTimeType();
        t.setValue(toXmlTime(value));
        return t;
    }

    default UBLExtensionsType emptyExtensions() {
        return new UBLExtensionsType();
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

    private XMLGregorianCalendar toXmlTime(String value) {
        java.time.LocalTime local = java.time.LocalTime.parse(value);
        try {
            return DatatypeFactory.newInstance()
                    .newXMLGregorianCalendarTime(
                            local.getHour(),
                            local.getMinute(),
                            local.getSecond(),
                            DatatypeConstants.FIELD_UNDEFINED,
                            DatatypeConstants.FIELD_UNDEFINED);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
