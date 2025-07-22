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
//    @Mapping(target = "buyerReference",
//            expression = "java(toBuyerReference(dto.getBuyerReference()))")
//
//    @Mapping(target = "invoicePeriod",
//            expression = "java(toInvoicePeriodList(dto))")
//    @Mapping(target = "contractDocumentReference",
//            expression = "java(toContractDocumentReferenceList(dto.getContractDocumentReferenceCbcId()))")
//
//    // Supplier
//    @Mapping(target = "accountingSupplierParty.party.endpointID",
//            expression = "java(toEndpointID(dto.getSupplierEndPoint()))")
//    @Mapping(target = "accountingSupplierParty.party.partyIdentification",
//            expression = "java(toPartyIdentificationList(dto.getSupplierPartyIdentificationCbcId()))")
//    @Mapping(target = "accountingSupplierParty.party.partyName",
//            expression = "java(toPartyNameList(dto.getSupplierPartyNameCbcName()))")
//    @Mapping(target = "accountingSupplierParty.party.postalAddress.streetName",
//            expression = "java(toStreetName(dto.getSupplierStreetName()))")
//    @Mapping(target = "accountingSupplierParty.party.postalAddress.additionalStreetName",
//            expression = "java(toAdditionalStreetName(dto.getSupplierAdditionalStreetName()))")
//    @Mapping(target = "accountingSupplierParty.party.postalAddress.cityName",
//            expression = "java(toCityName(dto.getSupplierCityName()))")
//    @Mapping(target = "accountingSupplierParty.party.postalAddress.postalZone",
//            expression = "java(toPostalZone(dto.getSupplierPostalZone()))")
//    @Mapping(target = "accountingSupplierParty.party.postalAddress.countrySubentity",
//            expression = "java(toCountrySubentity(dto.getSupplierCountrySubentity()))")
//    @Mapping(target = "accountingSupplierParty.party.postalAddress.addressLine",
//            expression = "java(toAddressLineList(dto.getSupplierAddressLineCbcLine()))")
//    @Mapping(target = "accountingSupplierParty.party.postalAddress.country.identificationCode",
//            expression = "java(toIdentificationCode(dto.getSupplierCountryCbcIdentificationCode()))")
//    @Mapping(target = "accountingSupplierParty.party.partyTaxScheme",
//            expression = "java(toPartyTaxSchemeList(dto.getSupplierPartyIdentificationCbcId(), dto.getSupplierPartyIdentificationCbcId()))")
//    @Mapping(target = "accountingSupplierParty.party.partyLegalEntity",
//            expression = "java(toPartyLegalEntityList(dto.getSupplierPartyNameCbcName(), dto.getSupplierPartyIdentificationCbcId(), null))")
//
//    // Customer
//    @Mapping(target = "accountingCustomerParty.party.endpointID",
//            expression = "java(toEndpointID(dto.getCustomerEndPoint()))")
//    @Mapping(target = "accountingCustomerParty.party.partyIdentification",
//            expression = "java(toPartyIdentificationList(dto.getCustomerPartyIdentificationCbcId()))")
//    @Mapping(target = "accountingCustomerParty.party.partyName",
//            expression = "java(toPartyNameList(dto.getCustomerPartyNameCbcName()))")
//    @Mapping(target = "accountingCustomerParty.party.postalAddress.streetName",
//            expression = "java(toStreetName(dto.getCustomerStreetName()))")
//    @Mapping(target = "accountingCustomerParty.party.postalAddress.additionalStreetName",
//            expression = "java(toAdditionalStreetName(dto.getCustomerAdditionalStreetName()))")
//    @Mapping(target = "accountingCustomerParty.party.postalAddress.cityName",
//            expression = "java(toCityName(dto.getCustomerCityName()))")
//    @Mapping(target = "accountingCustomerParty.party.postalAddress.postalZone",
//            expression = "java(toPostalZone(dto.getCustomerPostalZone()))")
//    @Mapping(target = "accountingCustomerParty.party.postalAddress.countrySubentity",
//            expression = "java(toCountrySubentity(dto.getCustomerCountrySubentity()))")
//    @Mapping(target = "accountingCustomerParty.party.postalAddress.addressLine",
//            expression = "java(toAddressLineList(dto.getCustomerAddressLineCbcLine()))")
//    @Mapping(target = "accountingCustomerParty.party.postalAddress.country.identificationCode",
//            expression = "java(toIdentificationCode(dto.getCustomerCountryCbcIdentificationCode()))")
//    @Mapping(target = "accountingCustomerParty.party.partyLegalEntity",
//            expression = "java(toPartyLegalEntityList(dto.getCustomerRegistrationName(), dto.getCustomerPartyIdentificationCbcId(), null))")
//    @Mapping(target = "accountingCustomerParty.party.contact",
//            expression = "java(toContact(dto.getCustomerPartyNameCbcName(), dto.getCustomerPartyIdentificationCbcId(), null))")
//
//    // Payment
//    @Mapping(target = "paymentMeans",
//            expression = "java(toPaymentMeansList(dto.getPaymentMeans(), dto.getPaymentMeansCbcPaymentId()))")
//
//    // Monetary totals
//    @Mapping(target = "legalMonetaryTotal",
//            expression = "java(toLegalMonetaryTotal(dto.getLegalMonetaryTotalCbcLineExtensionAmount(), "
//                    + "dto.getLegalMonetaryTotalCbcTaxExclusiveAmount(), "
//                    + "dto.getLegalMonetaryTotalCbcTaxInclusiveAmount(), "
//                    + "dto.getLegalMonetaryTotalCbcPayableAmount(), "
//                    + "dto.getCurrencyId()))")
//
//    // Invoice line
//    @Mapping(target = "invoiceLine", expression = "java(toInvoiceLineList(dto))")
//
//    // AllowanceCharge
//    @Mapping(target = "allowanceCharge", expression = "java(toAllowanceChargeList(dto))")
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

    default XMLGregorianCalendar toXmlDate(String dateStr) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("Invalid date: " + dateStr, e);
        }
    }
}
