package com.example.peppol.batch.csv;

import org.mapstruct.*;
import java.util.*;
import java.math.BigDecimal;

import network.oxalis.peppol.ubl2.jaxb.*;
import network.oxalis.peppol.ubl2.jaxb.cbc.*;
import network.oxalis.peppol.ubl2.jaxb.cac.*;
import com.example.peppol.batch.InvoiceDocument;

@Mapper(componentModel = "spring")
public interface InvoiceDocumentMapper {

    // ====== TO UBL INVOICE ======

    @Mapping(target = "ID", expression = "java(toID(doc.getInvoiceNumber()))")
    @Mapping(target = "issueDate", expression = "java(toIssueDate(doc.getIssueDate()))")
    @Mapping(target = "dueDate", expression = "java(toDueDate(doc.getDueDate()))")
    @Mapping(target = "invoiceTypeCode", expression = "java(toInvoiceTypeCode(doc.getInvoiceTypeCode()))")
    @Mapping(target = "note", expression = "java(toNoteList(doc.getNote()))")
    @Mapping(target = "buyerReference", expression = "java(toBuyerReference(doc.getBuyerReference()))")

    @Mapping(target = "invoicePeriod", expression = "java(toInvoicePeriodList(doc))")
    @Mapping(target = "contractDocumentReference", expression = "java(toContractDocRefList(doc.getContractDocumentReferenceCbcId()))")

    // ===== SUPPLIER (Party) =====
    @Mapping(target = "accountingSupplierParty.party.endpointID", expression = "java(toEndpointID(doc.getSupplierEndPoint()))")
    @Mapping(target = "accountingSupplierParty.party.partyIdentification", expression = "java(toPartyIdentificationList(doc.getSupplierPartyIdentificationCbcId()))")
    @Mapping(target = "accountingSupplierParty.party.partyName", expression = "java(toPartyNameList(doc.getSupplierPartyNameCbcName()))")
    @Mapping(target = "accountingSupplierParty.party.postalAddress.streetName", expression = "java(toStreetName(doc.getSupplierStreetName()))")
    @Mapping(target = "accountingSupplierParty.party.postalAddress.additionalStreetName", expression = "java(toAdditionalStreetName(doc.getSupplierAdditionalStreetName()))")
    @Mapping(target = "accountingSupplierParty.party.postalAddress.cityName", expression = "java(toCityName(doc.getSupplierCityName()))")
    @Mapping(target = "accountingSupplierParty.party.postalAddress.postalZone", expression = "java(toPostalZone(doc.getSupplierPostalZone()))")
    @Mapping(target = "accountingSupplierParty.party.postalAddress.countrySubentity", expression = "java(toCountrySubentity(doc.getSupplierCountrySubentity()))")
    @Mapping(target = "accountingSupplierParty.party.postalAddress.addressLine", expression = "java(toAddressLineList(doc.getSupplierAddressLineCbcLine()))")
    @Mapping(target = "accountingSupplierParty.party.postalAddress.country.identificationCode", expression = "java(toIdentificationCode(doc.getSupplierCountryCbcIdentificationCode()))")
    @Mapping(target = "accountingSupplierParty.party.partyTaxScheme", expression = "java(toPartyTaxSchemeList(doc.getSupplierPartyTaxSchemeCompanyId(), doc.getSupplierPartyTaxSchemeTaxSchemeId()))")
    @Mapping(target = "accountingSupplierParty.party.partyLegalEntity", expression = "java(toPartyLegalEntityList(doc.getSupplierPartyLegalEntityRegistrationName(), doc.getSupplierPartyLegalEntityCompanyId(), doc.getSupplierPartyLegalEntityCompanyLegalForm()))")

    // ===== CUSTOMER (Party) =====
    @Mapping(target = "accountingCustomerParty.party.endpointID", expression = "java(toEndpointID(doc.getCustomerEndPoint()))")
    @Mapping(target = "accountingCustomerParty.party.partyIdentification", expression = "java(toPartyIdentificationList(doc.getCustomerPartyIdentificationCbcId()))")
    @Mapping(target = "accountingCustomerParty.party.partyName", expression = "java(toPartyNameList(doc.getCustomerPartyNameCbcName()))")
    @Mapping(target = "accountingCustomerParty.party.postalAddress.streetName", expression = "java(toStreetName(doc.getCustomerStreetName()))")
    @Mapping(target = "accountingCustomerParty.party.postalAddress.additionalStreetName", expression = "java(toAdditionalStreetName(doc.getCustomerAdditionalStreetName()))")
    @Mapping(target = "accountingCustomerParty.party.postalAddress.cityName", expression = "java(toCityName(doc.getCustomerCityName()))")
    @Mapping(target = "accountingCustomerParty.party.postalAddress.postalZone", expression = "java(toPostalZone(doc.getCustomerPostalZone()))")
    @Mapping(target = "accountingCustomerParty.party.postalAddress.countrySubentity", expression = "java(toCountrySubentity(doc.getCustomerCountrySubentity()))")
    @Mapping(target = "accountingCustomerParty.party.postalAddress.addressLine", expression = "java(toAddressLineList(doc.getCustomerAddressLineCbcLine()))")
    @Mapping(target = "accountingCustomerParty.party.postalAddress.country.identificationCode", expression = "java(toIdentificationCode(doc.getCustomerCountryCbcIdentificationCode()))")
    @Mapping(target = "accountingCustomerParty.party.partyLegalEntity", expression = "java(toPartyLegalEntityList(doc.getCustomerPartyLegalEntityRegistrationName(), doc.getCustomerPartyLegalEntityCompanyId(), null))")
    @Mapping(target = "accountingCustomerParty.party.contact", expression = "java(toContact(doc.getCustomerContactName(), doc.getCustomerContactTelephone(), doc.getCustomerContactElectronicMail()))")

    // ===== PAYMENT =====
    @Mapping(target = "paymentMeans", expression = "java(toPaymentMeansList(doc))")

    // ===== MONETARY TOTALS =====
    @Mapping(target = "legalMonetaryTotal.lineExtensionAmount", expression = "java(toAmount(doc.getLegalMonetaryTotalCbcLineExtensionAmount()))")
    @Mapping(target = "legalMonetaryTotal.taxExclusiveAmount", expression = "java(toAmount(doc.getLegalMonetaryTotalCbcTaxExclusiveAmount()))")
    @Mapping(target = "legalMonetaryTotal.taxInclusiveAmount", expression = "java(toAmount(doc.getLegalMonetaryTotalCbcTaxInclusiveAmount()))")
    @Mapping(target = "legalMonetaryTotal.payableAmount", expression = "java(toAmount(doc.getLegalMonetaryTotalCbcPayableAmount()))")

    // ===== INVOICE LINE (simplified) =====
    @Mapping(target = "invoiceLine", expression = "java(toInvoiceLineList(doc))")

    // ===== ALLOWANCE/CHARGE (simplified) =====
    @Mapping(target = "allowanceCharge", expression = "java(toAllowanceChargeList(doc))")

    InvoiceType toInvoice(InvoiceDocument doc);

    // ====== FROM UBL INVOICE (reverse mapping) ======
    @InheritInverseConfiguration(name = "toInvoice")
    InvoiceDocument fromInvoice(InvoiceType invoice);

    // ====== HELPER METHODS ======

    default IDType toID(String value) {
        if (value == null) return null;
        IDType t = new IDType();
        t.setValue(value);
        return t;
    }
    default IssueDateType toIssueDate(String value) {
        if (value == null) return null;
        IssueDateType t = new IssueDateType();
        try {
            t.setValue(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(value));
        } catch (javax.xml.datatype.DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        return t;
    }
    default DueDateType toDueDate(String value) {
        if (value == null) return null;
        DueDateType t = new DueDateType();
        try {
            t.setValue(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(value));
        } catch (javax.xml.datatype.DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        return t;
    }
    default InvoiceTypeCodeType toInvoiceTypeCode(String value) {
        if (value == null) return null;
        InvoiceTypeCodeType t = new InvoiceTypeCodeType();
        t.setValue(value);
        return t;
    }
    default List<NoteType> toNoteList(String value) {
        if (value == null) return Collections.emptyList();
        NoteType n = new NoteType();
        n.setValue(value);
        return Collections.singletonList(n);
    }
    default BuyerReferenceType toBuyerReference(String value) {
        if (value == null) return null;
        BuyerReferenceType t = new BuyerReferenceType();
        t.setValue(value);
        return t;
    }
    default List<PeriodType> toInvoicePeriodList(InvoiceDocument doc) {
        if (doc.getStartDate() == null && doc.getEndDate() == null) return Collections.emptyList();
        PeriodType period = new PeriodType();
        if (doc.getStartDate() != null) {
            StartDateType s = new StartDateType();
            try {
                s.setValue(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(doc.getStartDate()));
            } catch (javax.xml.datatype.DatatypeConfigurationException e) {
                throw new RuntimeException(e);
            }
            period.setStartDate(s);
        }
        if (doc.getEndDate() != null) {
            EndDateType e = new EndDateType();
            try {
                e.setValue(javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(doc.getEndDate()));
            } catch (javax.xml.datatype.DatatypeConfigurationException ex) {
                throw new RuntimeException(ex);
            }
            period.setEndDate(e);
        }
        return Collections.singletonList(period);
    }
    default List<DocumentReferenceType> toContractDocRefList(String id) {
        if (id == null) return Collections.emptyList();
        DocumentReferenceType dr = new DocumentReferenceType();
        dr.setID(toID(id));
        return Collections.singletonList(dr);
    }
    default EndpointIDType toEndpointID(String value) {
        if (value == null) return null;
        EndpointIDType t = new EndpointIDType();
        t.setValue(value);
        return t;
    }
    default List<PartyIdentificationType> toPartyIdentificationList(String value) {
        if (value == null) return Collections.emptyList();
        PartyIdentificationType t = new PartyIdentificationType();
        IDType id = new IDType();
        id.setValue(value);
        t.setID(id);
        return Collections.singletonList(t);
    }
    default List<PartyNameType> toPartyNameList(String value) {
        if (value == null) return Collections.emptyList();
        PartyNameType n = new PartyNameType();
        NameType name = new NameType();
        name.setValue(value);
        n.setName(name);
        return Collections.singletonList(n);
    }
    default StreetNameType toStreetName(String value) {
        if (value == null) return null;
        StreetNameType t = new StreetNameType();
        t.setValue(value);
        return t;
    }
    default AdditionalStreetNameType toAdditionalStreetName(String value) {
        if (value == null) return null;
        AdditionalStreetNameType t = new AdditionalStreetNameType();
        t.setValue(value);
        return t;
    }
    default CityNameType toCityName(String value) {
        if (value == null) return null;
        CityNameType t = new CityNameType();
        t.setValue(value);
        return t;
    }
    default PostalZoneType toPostalZone(String value) {
        if (value == null) return null;
        PostalZoneType t = new PostalZoneType();
        t.setValue(value);
        return t;
    }
    default CountrySubentityType toCountrySubentity(String value) {
        if (value == null) return null;
        CountrySubentityType t = new CountrySubentityType();
        t.setValue(value);
        return t;
    }
    default List<AddressLineType> toAddressLineList(String value) {
        if (value == null) return Collections.emptyList();
        AddressLineType al = new AddressLineType();
        LineType line = new LineType();
        line.setValue(value);
        al.setLine(line);
        return Collections.singletonList(al);
    }
    default IdentificationCodeType toIdentificationCode(String value) {
        if (value == null) return null;
        IdentificationCodeType t = new IdentificationCodeType();
        t.setValue(value);
        return t;
    }
    default List<PartyTaxSchemeType> toPartyTaxSchemeList(String companyId, String taxSchemeId) {
        if (companyId == null && taxSchemeId == null) return Collections.emptyList();
        PartyTaxSchemeType pts = new PartyTaxSchemeType();
        if (companyId != null) {
            CompanyIDType cid = new CompanyIDType();
            cid.setValue(companyId);
            pts.setCompanyID(cid);
        }
        if (taxSchemeId != null) {
            TaxSchemeType tax = new TaxSchemeType();
            IDType id = new IDType();
            id.setValue(taxSchemeId);
            tax.setID(id);
            pts.setTaxScheme(tax);
        }
        return Collections.singletonList(pts);
    }
    default List<PartyLegalEntityType> toPartyLegalEntityList(String regName, String companyId, String companyLegalForm) {
        if (regName == null && companyId == null && companyLegalForm == null) return Collections.emptyList();
        PartyLegalEntityType ple = new PartyLegalEntityType();
        if (regName != null) {
            RegistrationNameType rn = new RegistrationNameType();
            rn.setValue(regName);
            ple.setRegistrationName(rn);
        }
        if (companyId != null) {
            CompanyIDType cid = new CompanyIDType();
            cid.setValue(companyId);
            ple.setCompanyID(cid);
        }
        if (companyLegalForm != null) {
            CompanyLegalFormType clf = new CompanyLegalFormType();
            clf.setValue(companyLegalForm);
            ple.setCompanyLegalForm(clf);
        }
        return Collections.singletonList(ple);
    }
    default ContactType toContact(String name, String tel, String mail) {
        if (name == null && tel == null && mail == null) return null;
        ContactType ct = new ContactType();
        if (name != null) {
            NameType n = new NameType();
            n.setValue(name);
            ct.setName(n);
        }
        if (tel != null) ct.setTelephone(tel);
        if (mail != null) ct.setElectronicMail(mail);
        return ct;
    }
    default List<PaymentMeansType> toPaymentMeansList(InvoiceDocument doc) {
        if (doc.getPaymentMeans() == null && doc.getPaymentMeansCbcPaymentId() == null) return Collections.emptyList();
        PaymentMeansType pm = new PaymentMeansType();
        if (doc.getPaymentMeans() != null) {
            PaymentMeansCodeType code = new PaymentMeansCodeType();
            code.setValue(doc.getPaymentMeans());
            pm.setPaymentMeansCode(code);
        }
        if (doc.getPaymentMeansCbcPaymentId() != null) {
            PaymentIDType pid = new PaymentIDType();
            pid.setValue(doc.getPaymentMeansCbcPaymentId());
            pm.getPaymentID().add(pid);
        }
        return Collections.singletonList(pm);
    }
    default AmountType toAmount(String value) {
        if (value == null) return null;
        AmountType amt = new AmountType();
        amt.setValue(new BigDecimal(value));
        return amt;
    }
    default List<InvoiceLineType> toInvoiceLineList(InvoiceDocument doc) {
        if (doc.getInvoiceLineCbcId() == null) return Collections.emptyList();
        InvoiceLineType il = new InvoiceLineType();
        IDType id = new IDType();
        id.setValue(doc.getInvoiceLineCbcId());
        il.setID(id);
        // Add further fields if needed!
        return Collections.singletonList(il);
    }
    default List<AllowanceChargeType> toAllowanceChargeList(InvoiceDocument doc) {
        if (doc.getAllowanceChargeCbcChargeIndicator() == null) return Collections.emptyList();
        AllowanceChargeType ac = new AllowanceChargeType();
        ChargeIndicatorType ci = new ChargeIndicatorType();
        ci.setValue(Boolean.valueOf(doc.getAllowanceChargeCbcChargeIndicator()));
        ac.setChargeIndicator(ci);
        if (doc.getAllowanceChargeCbcAmount() != null) {
            AmountType amt = new AmountType();
            amt.setValue(new BigDecimal(doc.getAllowanceChargeCbcAmount()));
            ac.setAmount(amt);
        }
        if (doc.getBaseAmountCbcCurrencyId() != null || doc.getAllowanceChargeCbcBaseAmount() != null) {
            BaseAmountType ba = new BaseAmountType();
            if (doc.getBaseAmountCbcCurrencyId() != null)
                ba.setCurrencyID(doc.getBaseAmountCbcCurrencyId());
            if (doc.getAllowanceChargeCbcBaseAmount() != null)
                ba.setValue(new BigDecimal(doc.getAllowanceChargeCbcBaseAmount()));
            ac.setBaseAmount(ba);
        }
        return Collections.singletonList(ac);
    }

    // You can add similar helpers for additional fields as needed

}
