package com.example.peppol.batch.csv;

import org.mapstruct.*;

import network.oxalis.peppol.ubl2.jaxb.CustomizationIDType;
import network.oxalis.peppol.ubl2.jaxb.IDType;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import network.oxalis.peppol.ubl2.jaxb.InvoiceTypeCodeType;
import network.oxalis.peppol.ubl2.jaxb.NoteType;
import network.oxalis.peppol.ubl2.jaxb.ProfileIDType;

/**
 * MapStruct mapper converting {@link CsvInvoiceRecord} instances to
 * {@link InvoiceType}.
 */
@Mapper
public interface CsvInvoiceMapper {
    CsvInvoiceMapper INSTANCE = Mappers.getMapper(CsvInvoiceMapper.class);

    @Mapping(target = "ID", expression = "java(toID(item.getId()))")
    @Mapping(target = "CustomizationID", expression = "java(toCustomizationID(item.getCustomizationId()))")
    @Mapping(target = "ProfileID", expression = "java(toProfileID(item.getProfileId()))")
    @Mapping(target = "InvoiceTypeCode", expression = "java(toInvoiceTypeCode(item.getInvoiceTypeCode()))")
    @Mapping(target = "note", expression = "java(item.getNote() == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(java.util.Collections.singletonList(toNote(item.getNote()))))")
    InvoiceType toInvoice(CsvInvoiceRecord item);

    @AfterMapping
    default void mapChassisProperty(CsvInvoiceRecord item, @MappingTarget InvoiceType invoice) {
        String val = item.getChassisValue();
        if (val == null) {
            return;
        }
        network.oxalis.peppol.ubl2.jaxb.InvoiceLineType line = new network.oxalis.peppol.ubl2.jaxb.InvoiceLineType();
        network.oxalis.peppol.ubl2.jaxb.ItemType itemType = new network.oxalis.peppol.ubl2.jaxb.ItemType();
        network.oxalis.peppol.ubl2.jaxb.ItemPropertyType prop = new network.oxalis.peppol.ubl2.jaxb.ItemPropertyType();
        network.oxalis.peppol.ubl2.jaxb.NameType name = new network.oxalis.peppol.ubl2.jaxb.NameType();
        name.setValue("chassis");
        prop.setName(name);
        network.oxalis.peppol.ubl2.jaxb.ValueType value = new network.oxalis.peppol.ubl2.jaxb.ValueType();
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
}
