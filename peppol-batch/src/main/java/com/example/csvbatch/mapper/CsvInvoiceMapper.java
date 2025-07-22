package com.example.csvbatch.mapper;

import com.example.csvbatch.model.CsvInvoiceDto;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import network.oxalis.peppol.ubl2.jaxb.cac.*;
import network.oxalis.peppol.ubl2.jaxb.cac.LocationType;
import network.oxalis.peppol.ubl2.jaxb.cbc.*;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CsvInvoiceMapper {

    @Mapping(target = "UBLVersionID", expression = "java(toUblVersionID(dto.getUblVersionID()))")
    @Mapping(target = "customizationID", expression = "java(toCustomizationID(dto.getCustomizationID()))")
    @Mapping(target = "profileID", expression = "java(toProfileID(dto.getProfileID()))")
    @Mapping(target = "ID", expression = "java(toID(dto.getInvoiceNumber()))")
    @Mapping(target = "UUID", expression = "java(toUUID(dto.getUuid()))")
    @Mapping(target = "issueDate", expression = "java(toIssueDate(dto.getIssueDate()))")
    @Mapping(target = "dueDate", expression = "java(toDueDate(dto.getDueDate()))")
    @Mapping(target = "invoiceTypeCode", expression = "java(toInvoiceTypeCode(dto.getInvoiceTypeCode()))")
    @Mapping(target = "note", expression = "java(toNoteList(dto.getNote()))")
    @Mapping(target = "documentCurrencyCode", expression = "java(toDocumentCurrencyCode(dto.getDocumentCurrencyCode()))")
    @Mapping(target = "lineCountNumeric", expression = "java(toLineCountNumeric(dto.getLineCountNumeric()))")
    @Mapping(target = "accountingSupplierParty", expression = "java(toAccountingSupplierParty(dto))")
    @Mapping(target = "accountingCustomerParty", expression = "java(toAccountingCustomerParty(dto))")
    @Mapping(target = "paymentMeans", expression = "java(toPaymentMeans(dto))")
    @Mapping(target = "taxTotal", expression = "java(toTaxTotal(dto))")
    @Mapping(target = "legalMonetaryTotal", expression = "java(toLegalMonetaryTotal(dto))")
    @Mapping(target = "invoiceLine", expression = "java(toInvoiceLineList(dto))")

    InvoiceType toInvoiceType(CsvInvoiceDto dto);

    default UBLVersionIDType toUblVersionID(String value) {
        if (value == null) return null;
        UBLVersionIDType t = new UBLVersionIDType();
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

    default IDType toID(String value) {
        if (value == null) return null;
        IDType id = new IDType();
        id.setValue(value);
        return id;
    }

    default UUIDType toUUID(String value) {
        if (value == null) return null;
        UUIDType t = new UUIDType();
        t.setValue(value);
        return t;
    }

    default IssueDateType toIssueDate(String value) {
        if (value == null) return null;
        IssueDateType type = new IssueDateType();
        type.setValue(toXmlDate(value));
        return type;
    }

    default DueDateType toDueDate(String value) {
        if (value == null) return null;
        DueDateType type = new DueDateType();
        type.setValue(toXmlDate(value));
        return type;
    }

    default InvoiceTypeCodeType toInvoiceTypeCode(String value) {
        if (value == null) return null;
        InvoiceTypeCodeType t = new InvoiceTypeCodeType();
        t.setValue(value);
        t.setListID("UNCL1001");
        t.setListAgencyID("6");
        return t;
    }

    default NoteType toNote(String value) {
        if (value == null) return null;
        NoteType note = new NoteType();
        note.setValue(value);
        return note;
    }

    default List<NoteType> toNoteList(String value) {
        if (value == null) return Collections.emptyList();
        NoteType t = new NoteType();
        t.setValue(value);
        return Collections.singletonList(t);
    }


    default DocumentCurrencyCodeType toDocumentCurrencyCode(String value) {
        if (value == null) return null;
        DocumentCurrencyCodeType t = new DocumentCurrencyCodeType();
        t.setValue(value);
        t.setListID("ISO4217");
        return t;
    }

    default LineCountNumericType toLineCountNumeric(String value) {
        if (value == null) return null;
        LineCountNumericType t = new LineCountNumericType();
        t.setValue(BigDecimal.valueOf(Long.parseLong(value)));
        return t;
    }

    default TelephoneType toTelephone(String value) {
        if (value == null) return null;
        TelephoneType tel = new TelephoneType();
        tel.setValue(value);
        return tel;
    }

    default SupplierPartyType toAccountingSupplierParty(CsvInvoiceDto dto) {
        SupplierPartyType supplier = new SupplierPartyType();
        PartyType party = new PartyType();

        // PartyIdentification
        PartyIdentificationType pid = new PartyIdentificationType();
        IDType partyId = new IDType();
        partyId.setValue(dto.getSupplierCompanyId());
        partyId.setSchemeID("BE:EN");
        pid.setID(partyId);
        party.getPartyIdentification().add(pid);

        // PartyName
        PartyNameType pname = new PartyNameType();
        NameType name = new NameType();
        name.setValue(dto.getSupplierName());
        pname.setName(name);
        party.getPartyName().add(pname);

        // PostalAddress
        AddressType address = new AddressType();
        StreetNameType street = new StreetNameType();
        street.setValue(dto.getSupplierStreet());
        address.setStreetName(street);

        CityNameType city = new CityNameType();
        city.setValue(dto.getSupplierCity());
        address.setCityName(city);

        PostalZoneType postal = new PostalZoneType();
        postal.setValue(dto.getSupplierPostal());
        address.setPostalZone(postal);

        CountryType country = new CountryType();
        IdentificationCodeType code = new IdentificationCodeType();
        code.setValue(dto.getSupplierCountryCode());
        code.setListID("ISO3166-1:Alpha2");
        code.setListAgencyID("6");
        country.setIdentificationCode(code);
        address.setCountry(country);
        party.setPostalAddress(address);

        // PhysicalLocation
        LocationType location = new LocationType();
        AddressType locAddress = new AddressType();
        locAddress.setStreetName(street);
        locAddress.setCityName(city);
        locAddress.setPostalZone(postal);
        locAddress.setCountry(country);
        location.setAddress(locAddress);
        party.setPhysicalLocation(location);

        // PartyTaxScheme
        PartyTaxSchemeType taxScheme = new PartyTaxSchemeType();
        CompanyIDType vat = new CompanyIDType();
        vat.setValue(dto.getSupplierVatId());
        vat.setSchemeID("BE:VAT");
        taxScheme.setCompanyID(vat);
        TaxSchemeType tax = new TaxSchemeType();
        IDType taxId = new IDType();
        taxId.setValue("VAT");
        taxId.setSchemeID("UN/ECE 5153");
        taxId.setSchemeAgencyID("6");
        tax.setID(taxId);
        taxScheme.setTaxScheme(tax);
        party.getPartyTaxScheme().add(taxScheme);

        // PartyLegalEntity
        PartyLegalEntityType legal = new PartyLegalEntityType();
        CompanyIDType legalId = new CompanyIDType();
        legalId.setValue(dto.getSupplierLegalId());
        legalId.setSchemeID("BE:EN");
        legal.setCompanyID(legalId);
        party.getPartyLegalEntity().add(legal);

        // Contact
        ContactType contact = new ContactType();
        contact.setTelephone(toTelephone(dto.getSupplierTelephone()));
        party.setContact(contact);

        supplier.setParty(party);
        return supplier;
    }

    default CustomerPartyType toAccountingCustomerParty(CsvInvoiceDto dto) {
        CustomerPartyType customer = new CustomerPartyType();
        PartyType party = new PartyType();

        // PartyIdentification
        PartyIdentificationType pid = new PartyIdentificationType();
        IDType partyId = new IDType();
        partyId.setValue(dto.getCustomerCompanyId());
        partyId.setSchemeID("BE:EN");
        pid.setID(partyId);
        party.getPartyIdentification().add(pid);

        // PartyName
        PartyNameType pname = new PartyNameType();
        NameType name = new NameType();
        name.setValue(dto.getCustomerName());
        pname.setName(name);
        party.getPartyName().add(pname);

        // PostalAddress
        AddressType address = new AddressType();
        StreetNameType street = new StreetNameType();
        street.setValue(dto.getCustomerStreet());
        address.setStreetName(street);

        CityNameType city = new CityNameType();
        city.setValue(dto.getCustomerCity());
        address.setCityName(city);

        PostalZoneType postal = new PostalZoneType();
        postal.setValue(dto.getCustomerPostal());
        address.setPostalZone(postal);

        CountryType country = new CountryType();
        IdentificationCodeType code = new IdentificationCodeType();
        code.setValue(dto.getCustomerCountryCode());
        code.setListID("ISO3166-1:Alpha2");
        code.setListAgencyID("6");
        country.setIdentificationCode(code);
        address.setCountry(country);
        party.setPostalAddress(address);

        // PhysicalLocation
        LocationType location = new LocationType();
        AddressType locAddress = new AddressType();
        locAddress.setStreetName(street);
        locAddress.setCityName(city);
        locAddress.setPostalZone(postal);
        locAddress.setCountry(country);
        location.setAddress(locAddress);
        party.setPhysicalLocation(location);

        // PartyTaxScheme
        PartyTaxSchemeType taxScheme = new PartyTaxSchemeType();
        CompanyIDType vat = new CompanyIDType();
        vat.setValue(dto.getCustomerVatId());
        vat.setSchemeID("BE:VAT");
        taxScheme.setCompanyID(vat);
        TaxSchemeType tax = new TaxSchemeType();
        IDType taxId = new IDType();
        taxId.setValue("VAT");
        taxId.setSchemeID("UN/ECE 5153");
        taxId.setSchemeAgencyID("6");
        tax.setID(taxId);
        taxScheme.setTaxScheme(tax);
        party.getPartyTaxScheme().add(taxScheme);

        // PartyLegalEntity
        PartyLegalEntityType legal = new PartyLegalEntityType();
        CompanyIDType legalId = new CompanyIDType();
        legalId.setValue(dto.getCustomerLegalId());
        legalId.setSchemeID("BE:EN");
        legal.setCompanyID(legalId);
        party.getPartyLegalEntity().add(legal);

        customer.setParty(party);
        return customer;
    }

    default List<PaymentMeansType> toPaymentMeans(CsvInvoiceDto dto) {
        PaymentMeansType pm = new PaymentMeansType();

        PaymentMeansCodeType code = new PaymentMeansCodeType();
        code.setValue(dto.getPaymentMeansCode());
        code.setListID("UNCL4461");
        code.setListAgencyID("6");
        pm.setPaymentMeansCode(code);

        PaymentDueDateType dueDate = new PaymentDueDateType();
        try {
            XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(dto.getPaymentDueDate());
            dueDate.setValue(calendar);
            pm.setPaymentDueDate(dueDate);
        } catch (Exception e) {
            throw new RuntimeException("Invalid payment due date", e);
        }

        PaymentIDType paymentId = new PaymentIDType();
        paymentId.setValue(dto.getPaymentId());
        pm.getPaymentID().add(paymentId);

        FinancialAccountType account = new FinancialAccountType();
        IDType accId = new IDType();
        accId.setSchemeID("IBAN");
        accId.setValue(dto.getPayeeAccountId());
        account.setID(accId);

        NameType name = new NameType();
        name.setValue(dto.getPayeeAccountName());
        account.setName(name);

        pm.setPayeeFinancialAccount(account);

        return Collections.singletonList(pm);
    }

    default List<TaxTotalType> toTaxTotal(CsvInvoiceDto dto) {
        TaxTotalType taxTotal = new TaxTotalType();

        TaxAmountType totalAmount = new TaxAmountType();
        totalAmount.setCurrencyID("EUR");
        totalAmount.setValue(new BigDecimal(dto.getTaxTotalAmount()));
        taxTotal.setTaxAmount(totalAmount);

        TaxSubtotalType subtotal = new TaxSubtotalType();

        TaxableAmountType taxableAmount = new TaxableAmountType();
        taxableAmount.setCurrencyID("EUR");
        taxableAmount.setValue(new BigDecimal(dto.getTaxSubtotalTaxableAmount()));
        subtotal.setTaxableAmount(taxableAmount);

        TaxAmountType taxAmount = new TaxAmountType();
        taxAmount.setCurrencyID("EUR");
        taxAmount.setValue(new BigDecimal(dto.getTaxSubtotalTaxAmount()));
        subtotal.setTaxAmount(taxAmount);

        PercentType subtotalPercent = new PercentType();
        subtotalPercent.setValue(new BigDecimal(dto.getTaxSubtotalPercent()));
        subtotal.setPercent(subtotalPercent);

        TaxCategoryType category = new TaxCategoryType();
        IDType catId = new IDType();
        catId.setSchemeAgencyID("6");
        catId.setSchemeID("UNCL5305");
        catId.setValue(dto.getTaxCategoryID());
        category.setID(catId);

        NameType name = new NameType();
        name.setValue(dto.getTaxCategoryName());
        category.setName(name);

        PercentType catPercent = new PercentType();
        catPercent.setValue(new BigDecimal(dto.getTaxCategoryPercent()));
        category.setPercent(catPercent);

        TaxSchemeType scheme = new TaxSchemeType();
        IDType schemeId = new IDType();
        schemeId.setSchemeAgencyID("6");
        schemeId.setSchemeID("UN/ECE 5153");
        schemeId.setValue(dto.getTaxSchemeID());
        scheme.setID(schemeId);

        category.setTaxScheme(scheme);
        subtotal.setTaxCategory(category);

        taxTotal.getTaxSubtotal().add(subtotal);
        return Collections.singletonList(taxTotal);
    }

    default MonetaryTotalType toLegalMonetaryTotal(CsvInvoiceDto dto) {
        MonetaryTotalType total = new MonetaryTotalType();

        LineExtensionAmountType lineExt = new LineExtensionAmountType();
        lineExt.setCurrencyID("EUR");
        lineExt.setValue(new BigDecimal(dto.getLineExtensionAmount()));
        total.setLineExtensionAmount(lineExt);

        TaxExclusiveAmountType taxExcl = new TaxExclusiveAmountType();
        taxExcl.setCurrencyID("EUR");
        taxExcl.setValue(new BigDecimal(dto.getTaxExclusiveAmount()));
        total.setTaxExclusiveAmount(taxExcl);

        TaxInclusiveAmountType taxIncl = new TaxInclusiveAmountType();
        taxIncl.setCurrencyID("EUR");
        taxIncl.setValue(new BigDecimal(dto.getTaxInclusiveAmount()));
        total.setTaxInclusiveAmount(taxIncl);

        PayableAmountType payable = new PayableAmountType();
        payable.setCurrencyID("EUR");
        payable.setValue(new BigDecimal(dto.getPayableAmount()));
        total.setPayableAmount(payable);

        return total;
    }


    default List<InvoiceLineType> toInvoiceLineList(CsvInvoiceDto dto) {
        InvoiceLineType line = new InvoiceLineType();

        IDType id = new IDType();
        id.setValue(dto.getInvoiceLineId());
        line.setID(id);

        UUIDType uuid = new UUIDType();
        uuid.setValue(dto.getInvoiceLineUuid());
        line.setUUID(uuid);

        line.getNote().add(toNote(dto.getInvoiceLineNote1()));
        line.getNote().add(toNote(dto.getInvoiceLineNote2()));

        InvoicedQuantityType quantity = new InvoicedQuantityType();
        quantity.setUnitCode(dto.getInvoiceLineUnitCode());
        quantity.setValue(new BigDecimal(dto.getInvoiceLineQuantity()));
        line.setInvoicedQuantity(quantity);

        LineExtensionAmountType extAmount = new LineExtensionAmountType();
        extAmount.setCurrencyID("EUR");
        extAmount.setValue(new BigDecimal(dto.getInvoiceLineLineExtensionAmount()));
        line.setLineExtensionAmount(extAmount);

        TaxTotalType taxTotal = new TaxTotalType();
        TaxAmountType taxAmount = new TaxAmountType();
        taxAmount.setCurrencyID("EUR");
        taxAmount.setValue(new BigDecimal(dto.getInvoiceLineTaxAmount()));
        taxTotal.setTaxAmount(taxAmount);

        TaxSubtotalType subtotal = new TaxSubtotalType();
        TaxableAmountType taxable = new TaxableAmountType();
        taxable.setCurrencyID("EUR");
        taxable.setValue(new BigDecimal(dto.getInvoiceLineTaxableAmount()));
        subtotal.setTaxableAmount(taxable);

        TaxAmountType subTax = new TaxAmountType();
        subTax.setCurrencyID("EUR");
        subTax.setValue(new BigDecimal(dto.getInvoiceLineTaxAmount()));
        subtotal.setTaxAmount(subTax);

        PercentType percent = new PercentType();
        percent.setValue(new BigDecimal(dto.getInvoiceLineTaxPercent()));
        subtotal.setPercent(percent);

        TaxCategoryType category = new TaxCategoryType();
        IDType catId = new IDType();
        catId.setSchemeAgencyID("6");
        catId.setSchemeID("UNCL5305");
        catId.setValue(dto.getInvoiceLineTaxCategoryId());
        category.setID(catId);

        NameType name = new NameType();
        name.setValue(dto.getInvoiceLineTaxCategoryName());
        category.setName(name);

        PercentType catPercent = new PercentType();
        catPercent.setValue(new BigDecimal(dto.getInvoiceLineTaxCategoryPercent()));
        category.setPercent(catPercent);

        TaxSchemeType scheme = new TaxSchemeType();
        IDType schemeId = new IDType();
        schemeId.setSchemeAgencyID("6");
        schemeId.setSchemeID("UN/ECE 5153");
        schemeId.setValue(dto.getInvoiceLineTaxSchemeId());
        scheme.setID(schemeId);

        category.setTaxScheme(scheme);
        subtotal.setTaxCategory(category);
        taxTotal.getTaxSubtotal().add(subtotal);
        line.getTaxTotal().add(taxTotal);

        ItemType item = new ItemType();
        NameType itemName = new NameType();
        itemName.setValue(dto.getInvoiceLineItemName());
        item.setName(itemName);

        ItemIdentificationType sellerItem = new ItemIdentificationType();
        IDType itemId = new IDType();
        itemId.setValue(dto.getInvoiceLineItemId());
        sellerItem.setID(itemId);
        item.setSellersItemIdentification(sellerItem);
        line.setItem(item);

        PriceType price = new PriceType();
        PriceAmountType priceAmount = new PriceAmountType();
        priceAmount.setCurrencyID("EUR");
        priceAmount.setValue(new BigDecimal(dto.getInvoiceLinePriceAmount()));
        price.setPriceAmount(priceAmount);
        line.setPrice(price);

        return Collections.singletonList(line);
    }

    default XMLGregorianCalendar toXmlDate(String dateStr) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("Invalid date: " + dateStr, e);
        }
    }
}
