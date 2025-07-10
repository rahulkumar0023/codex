package com.example.peppol.batch.csv;

import org.mapstruct.*;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import network.oxalis.peppol.ubl2.jaxb.cbc.CustomizationIDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.DocumentCurrencyCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.DueDateType;
import network.oxalis.peppol.ubl2.jaxb.cbc.IDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.InvoiceTypeCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.IssueDateType;
import network.oxalis.peppol.ubl2.jaxb.cbc.NoteType;
import network.oxalis.peppol.ubl2.jaxb.cbc.ProfileIDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.TaxCurrencyCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.TaxPointDateType;
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
    @Mapping(target = "note", expression = "java(item.getNote() == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(java.util.Collections.singletonList(toNote(item.getNote()))))")
    InvoiceType toInvoice(CsvInvoiceRecord item);

    @AfterMapping
    default void mapChassisProperty(CsvInvoiceRecord item, @MappingTarget InvoiceType invoice) {
        String val = item.getChassisValue();
        if (val == null) {
            return;
        }
        network.oxalis.peppol.ubl2.jaxb.cac.InvoiceLineType line = new network.oxalis.peppol.ubl2.jaxb.cac.InvoiceLineType();
        network.oxalis.peppol.ubl2.jaxb.cac.ItemType itemType = new network.oxalis.peppol.ubl2.jaxb.cac.ItemType();
        network.oxalis.peppol.ubl2.jaxb.cac.ItemPropertyType prop = new network.oxalis.peppol.ubl2.jaxb.cac.ItemPropertyType();
        network.oxalis.peppol.ubl2.jaxb.cbc.NameType name = new network.oxalis.peppol.ubl2.jaxb.cbc.NameType();
        name.setValue("chassis");
        prop.setName(name);
        network.oxalis.peppol.ubl2.jaxb.cbc.ValueType value = new network.oxalis.peppol.ubl2.jaxb.cbc.ValueType();
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
        t.setValue(java.time.LocalDate.parse(value));
        return t;
    }

    default DueDateType toDueDate(String value) {
        if (value == null) return null;
        DueDateType t = new DueDateType();
        t.setValue(java.time.LocalDate.parse(value));
        return t;
    }

    default TaxPointDateType toTaxPointDate(String value) {
        if (value == null) return null;
        TaxPointDateType t = new TaxPointDateType();
        t.setValue(java.time.LocalDate.parse(value));
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
}
