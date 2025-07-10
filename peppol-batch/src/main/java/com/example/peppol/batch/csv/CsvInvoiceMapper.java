package com.example.peppol.batch.csv;

import org.mapstruct.*;

import network.oxalis.peppol.ubl2.jaxb.cbc.CustomizationIDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.IDType;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import network.oxalis.peppol.ubl2.jaxb.cbc.InvoiceTypeCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.NoteType;
import network.oxalis.peppol.ubl2.jaxb.cbc.ProfileIDType;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper converting {@link CsvInvoiceRecord} instances to
 * {@link InvoiceType}.
 */
@Mapper(builder = @Builder(disableBuilder = true))
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
}
