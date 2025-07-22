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
    @Mapping(target = "buyerReference", expression = "java(toBuyerReference(dto.getBuyerReference()))")
//    @Mapping(target = "documentCurrencyCode", expression = "java(toDocumentCurrencyCode(dto.getDocumentCurrencyCode()))")
//    @Mapping(target = "lineCountNumeric", expression = "java(toLineCountNumeric(dto.getLineCountNumeric()))")
    @Mapping(target = "accountingSupplierParty", expression = "java(toAccountingSupplierParty(dto))")
    @Mapping(target = "accountingCustomerParty", expression = "java(toAccountingCustomerParty(dto))")
    @Mapping(target = "paymentMeans", expression = "java(toPaymentMeans(dto))")
    //@Mapping(target = "taxTotal", expression = "java(toTaxTotal(dto))")
    @Mapping(target = "legalMonetaryTotal", expression = "java(toLegalMonetaryTotal(dto))")
    @Mapping(target = "invoiceLine", expression = "java(toInvoiceLineList(dto))")
    @Mapping(target = "invoicePeriod", expression = "java(toInvoicePeriodList(dto))")
    @Mapping(target = "allowanceCharge", expression = "java(toAllowanceChargeList(dto))")
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

    default BuyerReferenceType toBuyerReference(String value) {
        if (value == null) return null;
        BuyerReferenceType ref = new BuyerReferenceType();
        ref.setValue(value);
        return ref;
    }

//    default DocumentCurrencyCodeType toDocumentCurrencyCode(String value) {
//        if (value == null) return null;
//        DocumentCurrencyCodeType t = new DocumentCurrencyCodeType();
//        t.setValue(value);
//        t.setListID("ISO4217");
//        return t;
//    }
//
//    default LineCountNumericType toLineCountNumeric(String value) {
//        if (value == null) return null;
//        LineCountNumericType t = new LineCountNumericType();
//        t.setValue(BigDecimal.valueOf(Long.parseLong(value)));
//        return t;
//    }

    default TelephoneType toTelephone(String value) {
        if (value == null) return null;
        TelephoneType tel = new TelephoneType();
        tel.setValue(value);
        return tel;
    }

    default SupplierPartyType toAccountingSupplierParty(CsvInvoiceDto dto) {
        SupplierPartyType supplier = new SupplierPartyType();
        PartyType party = new PartyType();

        // EndpointID
        if (dto.getSupplierEndPoint() != null) {
            EndpointIDType endpointID = new EndpointIDType();
            endpointID.setValue(dto.getSupplierEndPoint());
            party.setEndpointID(endpointID);
        }

        // PartyIdentification
        if (dto.getSupplierPartyIdentificationCbcId() != null) {
            PartyIdentificationType pid = new PartyIdentificationType();
            IDType partyId = new IDType();
            partyId.setValue(dto.getSupplierPartyIdentificationCbcId());
            pid.setID(partyId);
            party.getPartyIdentification().add(pid);
        }

        // PartyName
        if (dto.getSupplierPartyNameCbcName() != null) {
            PartyNameType pname = new PartyNameType();
            NameType name = new NameType();
            name.setValue(dto.getSupplierPartyNameCbcName());
            pname.setName(name);
            party.getPartyName().add(pname);
        }

        // PostalAddress
        AddressType address = new AddressType();

        if (dto.getSupplierStreetName() != null) {
            StreetNameType street = new StreetNameType();
            street.setValue(dto.getSupplierStreetName());
            address.setStreetName(street);
        }

        if (dto.getSupplierAdditionalStreetName() != null) {
            AdditionalStreetNameType addStreet = new AdditionalStreetNameType();
            addStreet.setValue(dto.getSupplierAdditionalStreetName());
            address.setAdditionalStreetName(addStreet);
        }

        if (dto.getSupplierCityName() != null) {
            CityNameType city = new CityNameType();
            city.setValue(dto.getSupplierCityName());
            address.setCityName(city);
        }

        if (dto.getSupplierPostalZone() != null) {
            PostalZoneType postal = new PostalZoneType();
            postal.setValue(dto.getSupplierPostalZone());
            address.setPostalZone(postal);
        }

        if (dto.getSupplierCountrySubentity() != null) {
            CountrySubentityType subentity = new CountrySubentityType();
            subentity.setValue(dto.getSupplierCountrySubentity());
            address.setCountrySubentity(subentity);
        }

        if (dto.getSupplierAddressLineCbcLine() != null) {
            AddressLineType line = new AddressLineType();
            LineType l = new LineType();
            l.setValue(dto.getSupplierAddressLineCbcLine());
            line.setLine(l);
            address.getAddressLine().add(line);
        }

        if (dto.getSupplierCountryCbcIdentificationCode() != null) {
            CountryType country = new CountryType();
            IdentificationCodeType code = new IdentificationCodeType();
            code.setValue(dto.getSupplierCountryCbcIdentificationCode());
            code.setListID("ISO3166-1:Alpha2");
            code.setListAgencyID("6");
            country.setIdentificationCode(code);
            address.setCountry(country);
        }

        party.setPostalAddress(address);
        supplier.setParty(party);
        return supplier;
    }


    default CustomerPartyType toAccountingCustomerParty(CsvInvoiceDto dto) {
        CustomerPartyType customer = new CustomerPartyType();
        PartyType party = new PartyType();

        // EndpointID
        if (dto.getCustomerEndPoint() != null) {
            EndpointIDType endpointID = new EndpointIDType();
            endpointID.setValue(dto.getCustomerEndPoint());
            party.setEndpointID(endpointID);
        }

        // PartyIdentification
        if (dto.getCustomerPartyIdentificationCbcId() != null) {
            PartyIdentificationType pid = new PartyIdentificationType();
            IDType partyId = new IDType();
            partyId.setValue(dto.getCustomerPartyIdentificationCbcId());
            pid.setID(partyId);
            party.getPartyIdentification().add(pid);
        }

        // PartyName
        if (dto.getCustomerPartyNameCbcName() != null) {
            PartyNameType pname = new PartyNameType();
            NameType name = new NameType();
            name.setValue(dto.getCustomerPartyNameCbcName());
            pname.setName(name);
            party.getPartyName().add(pname);
        }

        // PostalAddress
        AddressType address = new AddressType();

        if (dto.getCustomerStreetName() != null) {
            StreetNameType street = new StreetNameType();
            street.setValue(dto.getCustomerStreetName());
            address.setStreetName(street);
        }

        if (dto.getCustomerAdditionalStreetName() != null) {
            AdditionalStreetNameType addStreet = new AdditionalStreetNameType();
            addStreet.setValue(dto.getCustomerAdditionalStreetName());
            address.setAdditionalStreetName(addStreet);
        }

        if (dto.getCustomerCityName() != null) {
            CityNameType city = new CityNameType();
            city.setValue(dto.getCustomerCityName());
            address.setCityName(city);
        }

        if (dto.getCustomerPostalZone() != null) {
            PostalZoneType postal = new PostalZoneType();
            postal.setValue(dto.getCustomerPostalZone());
            address.setPostalZone(postal);
        }

        if (dto.getCustomerCountrySubentity() != null) {
            CountrySubentityType subentity = new CountrySubentityType();
            subentity.setValue(dto.getCustomerCountrySubentity());
            address.setCountrySubentity(subentity);
        }

        if (dto.getCustomerAddressLineCbcLine() != null) {
            AddressLineType line = new AddressLineType();
            LineType l = new LineType();
            l.setValue(dto.getCustomerAddressLineCbcLine());
            line.setLine(l);
            address.getAddressLine().add(line);
        }

        if (dto.getCustomerCountryCbcIdentificationCode() != null) {
            CountryType country = new CountryType();
            IdentificationCodeType code = new IdentificationCodeType();
            code.setValue(dto.getCustomerCountryCbcIdentificationCode());
            code.setListID("ISO3166-1:Alpha2");
            code.setListAgencyID("6");
            country.setIdentificationCode(code);
            address.setCountry(country);
        }

        party.setPostalAddress(address);

        // PartyLegalEntity
        if (dto.getCustomerRegistrationName() != null) {
            PartyLegalEntityType legal = new PartyLegalEntityType();
            RegistrationNameType reg = new RegistrationNameType();
            reg.setValue(dto.getCustomerRegistrationName());
            legal.setRegistrationName(reg);
            party.getPartyLegalEntity().add(legal);
        }

        customer.setParty(party);
        return customer;
    }


    default List<PaymentMeansType> toPaymentMeans(CsvInvoiceDto dto) {
        if (dto.getPaymentMeansCode() == null && dto.getPaymentMeansCbcPaymentId() == null) {
            return Collections.emptyList();
        }

        PaymentMeansType pm = new PaymentMeansType();

        // PaymentMeansCode
        if (dto.getPaymentMeansCode() != null) {
            PaymentMeansCodeType code = new PaymentMeansCodeType();
            code.setValue(dto.getPaymentMeansCode());
            code.setListID("UNCL4461");
            code.setListAgencyID("6");
            pm.setPaymentMeansCode(code);
        }

        // PaymentID
        if (dto.getPaymentMeansCbcPaymentId() != null) {
            PaymentIDType pid = new PaymentIDType();
            pid.setValue(dto.getPaymentMeansCbcPaymentId());
            pm.getPaymentID().add(pid);
        }

        return Collections.singletonList(pm);
    }



    default MonetaryTotalType toLegalMonetaryTotal(CsvInvoiceDto dto) {
        MonetaryTotalType total = new MonetaryTotalType();

        if (dto.getLegalMonetaryTotalCbcLineExtensionAmount() != null) {
            LineExtensionAmountType lineAmt = new LineExtensionAmountType();
            lineAmt.setValue(new BigDecimal(dto.getLegalMonetaryTotalCbcLineExtensionAmount()));
            lineAmt.setCurrencyID("EUR");
            total.setLineExtensionAmount(lineAmt);
        }

        if (dto.getLegalMonetaryTotalCbcTaxExclusiveAmount() != null) {
            TaxExclusiveAmountType taxExclAmt = new TaxExclusiveAmountType();
            taxExclAmt.setValue(new BigDecimal(dto.getLegalMonetaryTotalCbcTaxExclusiveAmount()));
            taxExclAmt.setCurrencyID("EUR");
            total.setTaxExclusiveAmount(taxExclAmt);
        }

        if (dto.getLegalMonetaryTotalCbcTaxInclusiveAmount() != null) {
            TaxInclusiveAmountType taxInclAmt = new TaxInclusiveAmountType();
            taxInclAmt.setValue(new BigDecimal(dto.getLegalMonetaryTotalCbcTaxInclusiveAmount()));
            taxInclAmt.setCurrencyID("EUR");
            total.setTaxInclusiveAmount(taxInclAmt);
        }

        if (dto.getLegalMonetaryTotalCbcPayableAmount() != null) {
            PayableAmountType payableAmt = new PayableAmountType();
            payableAmt.setValue(new BigDecimal(dto.getLegalMonetaryTotalCbcPayableAmount()));
            payableAmt.setCurrencyID("EUR");
            total.setPayableAmount(payableAmt);
        }

        return total;
    }

    default List<InvoiceLineType> toInvoiceLineList(CsvInvoiceDto dto) {
        if (dto.getInvoiceLineCbcId() == null) {
            return Collections.emptyList();
        }

        InvoiceLineType line = new InvoiceLineType();

        // ID
        IDType id = new IDType();
        id.setValue(dto.getInvoiceLineCbcId());
        line.setID(id);

        // Invoiced Quantity
        if (dto.getInvoiceLineCbcInvoicedQuantity() != null) {
            InvoicedQuantityType qty = new InvoicedQuantityType();
            qty.setValue(new BigDecimal(dto.getInvoiceLineCbcInvoicedQuantity()));
            qty.setUnitCode("ZZ");
            line.setInvoicedQuantity(qty);
        }

        // Line Extension Amount
        if (dto.getInvoiceLineCbcLineExtensionAmount() != null) {
            LineExtensionAmountType amt = new LineExtensionAmountType();
            amt.setValue(new BigDecimal(dto.getInvoiceLineCbcLineExtensionAmount()));
            amt.setCurrencyID(dto.getCurrencyId() != null ? dto.getCurrencyId() : "EUR");
            line.setLineExtensionAmount(amt);
        }

        // Item
        ItemType item = new ItemType();
        if (dto.getItemCbcName() != null) {
            NameType name = new NameType();
            name.setValue(dto.getItemCbcName());
            item.setName(name);
        }
        if (dto.getDescriptionCbcItem() != null) {
            DescriptionType desc = new DescriptionType();
            desc.setValue(dto.getDescriptionCbcItem());
            item.getDescription().add(desc);
        }
        line.setItem(item);
        return Collections.singletonList(line);
    }

    default List<AllowanceChargeType> toAllowanceChargeList(CsvInvoiceDto dto) {
        if (dto.getAllowanceChargeCbcChargeIndicator() == null) return Collections.emptyList();

        AllowanceChargeType charge = new AllowanceChargeType();

        // ChargeIndicator
        ChargeIndicatorType chargeIndicator = new ChargeIndicatorType();
        chargeIndicator.setValue(Boolean.parseBoolean(dto.getAllowanceChargeCbcChargeIndicator()));
        charge.setChargeIndicator(chargeIndicator);

        // Amount
        if (dto.getAllowanceChargeCbcAmount() != null) {
            AmountType amt = new AmountType();
            amt.setValue(new BigDecimal(dto.getAllowanceChargeCbcAmount()));
            amt.setCurrencyID(dto.getBaseAmountCbcCurrencyId());
            charge.setAmount(amt);
        }

        // BaseAmount
        if (dto.getAllowanceChargeCbcBaseAmount() != null) {
            BaseAmountType base = new BaseAmountType();
            base.setValue(new BigDecimal(dto.getAllowanceChargeCbcBaseAmount()));
            base.setCurrencyID(dto.getBaseAmountCbcCurrencyId() != null ? dto.getBaseAmountCbcCurrencyId() : "EUR");
            charge.setBaseAmount(base);
        }

        // Reason
        if (dto.getAllowanceChargeReason() != null) {
            AllowanceChargeReasonType reason = new AllowanceChargeReasonType();
            reason.setValue(dto.getAllowanceChargeReason());
            charge.getAllowanceChargeReason().add(reason);
        }

        return Collections.singletonList(charge);
    }


    default List<PeriodType> toInvoicePeriodList(CsvInvoiceDto dto) {
        if (dto.getInvoicePeriodCbcStartDate() == null && dto.getInvoicePeriodCbcEndDate() == null)
            return Collections.emptyList();

        PeriodType period = new PeriodType();
        if (dto.getInvoicePeriodCbcStartDate() != null) {
            StartDateType start = new StartDateType();
            start.setValue(toXmlDate(dto.getInvoicePeriodCbcStartDate()));
            period.setStartDate(start);
        }

        if (dto.getInvoicePeriodCbcEndDate() != null) {
            EndDateType end = new EndDateType();
            end.setValue(toXmlDate(dto.getInvoicePeriodCbcEndDate()));
            period.setEndDate(end);
        }

        return Collections.singletonList(period);
    }

    default XMLGregorianCalendar toXmlDate(String value) {
        if (value == null || value.trim().equalsIgnoreCase("value")) {
            throw new RuntimeException("Invalid date: " + value);
        }
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(value);
        } catch (Exception e) {
            throw new RuntimeException("Invalid date: " + value, e);
        }
    }
}
