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
import network.oxalis.peppol.ubl2.jaxb.cbc.IDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.InvoiceTypeCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.IssueDateType;
import network.oxalis.peppol.ubl2.jaxb.cbc.NoteType;
import network.oxalis.peppol.ubl2.jaxb.cbc.ProfileIDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.TaxCurrencyCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.TaxPointDateType;
import network.oxalis.peppol.ubl2.jaxb.cbc.AccountingCostType;
import network.oxalis.peppol.ubl2.jaxb.cbc.BuyerReferenceType;
import network.oxalis.peppol.ubl2.jaxb.cac.PeriodType;
import network.oxalis.peppol.ubl2.jaxb.cac.DocumentReferenceType;
import network.oxalis.peppol.ubl2.jaxb.cac.AttachmentType;
import network.oxalis.peppol.ubl2.jaxb.cac.ExternalReferenceType;
import network.oxalis.peppol.ubl2.jaxb.cac.PaymentMeansType;
import network.oxalis.peppol.ubl2.jaxb.cac.PaymentTermsType;
import network.oxalis.peppol.ubl2.jaxb.cac.FinancialAccountType;
import network.oxalis.peppol.ubl2.jaxb.cac.BranchType;
import network.oxalis.peppol.ubl2.jaxb.cbc.StartDateType;
import network.oxalis.peppol.ubl2.jaxb.cbc.EndDateType;
import network.oxalis.peppol.ubl2.jaxb.cbc.PaymentMeansCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.PaymentIDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.NameType;
import network.oxalis.peppol.ubl2.jaxb.cbc.URIType;
import network.oxalis.peppol.ubl2.jaxb.cbc.DocumentTypeCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.DocumentDescriptionType;
import network.oxalis.peppol.ubl2.jaxb.cbc.EndpointIDType;
import network.oxalis.peppol.ubl2.jaxb.cbc.StreetNameType;
import network.oxalis.peppol.ubl2.jaxb.cbc.AdditionalStreetNameType;
import network.oxalis.peppol.ubl2.jaxb.cbc.CityNameType;
import network.oxalis.peppol.ubl2.jaxb.cbc.PostalZoneType;
import network.oxalis.peppol.ubl2.jaxb.cbc.CountrySubentityType;
import network.oxalis.peppol.ubl2.jaxb.cbc.LineType;
import network.oxalis.peppol.ubl2.jaxb.cbc.IdentificationCodeType;
import network.oxalis.peppol.ubl2.jaxb.cbc.RegistrationNameType;
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
import com.example.peppol.batch.InvoiceDocument;

import network.oxalis.peppol.ubl2.jaxb.ecdt.UBLExtensionsType;

/**
 * MapStruct mapper converting {@link CsvInvoiceRecord} instances to
 * {@link InvoiceType}.
 */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CsvInvoiceMapper {

//    @Mapping(target = "ID", expression = "java(toID(item.getId()))")
//    @Mapping(target = "customizationID", expression = "java(toCustomizationID(item.getCustomizationID()))")
//    @Mapping(target = "profileID", expression = "java(toProfileID(item.getProfileID()))")
    @Mapping(target = "invoiceTypeCode", expression = "java(toInvoiceTypeCode(item.getInvoiceTypeCode()))")
    @Mapping(target = "issueDate", expression = "java(toIssueDate(item.getIssueDate()))")
    @Mapping(target = "dueDate", expression = "java(toDueDate(item.getDueDate()))")
    @Mapping(target = "taxPointDate", expression = "java(toTaxPointDate(item.getTaxPointDate()))")
    @Mapping(target = "documentCurrencyCode", expression = "java(toDocumentCurrencyCode(item.getDocumentCurrencyCode()))")
    @Mapping(target = "taxCurrencyCode", expression = "java(toTaxCurrencyCode(item.getTaxCurrencyCode()))")
    @Mapping(target = "accountingCost", expression = "java(toAccountingCost(item.getAccountingCost()))")
    @Mapping(target = "buyerReference", expression = "java(toBuyerReference(item.getBuyerReference()))")
    @Mapping(target = "invoicePeriod", expression = "java(toInvoicePeriod(item))")
    @Mapping(target = "contractDocumentReference", expression = "java(toContractDocumentReference(item))")
    @Mapping(target = "additionalDocumentReference", expression = "java(toAdditionalDocumentReferences(item))")
    @Mapping(target = "paymentMeans", expression = "java(toPaymentMeans(item))")
    @Mapping(target = "paymentTerms", expression = "java(toPaymentTerms(item))")
    @Mapping(target = "UBLExtensions", expression = "java(emptyExtensions())")
    @Mapping(target = "note", expression = "java(item.getNote() == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(java.util.Collections.singletonList(toNote(item.getNote()))))")
    InvoiceType toInvoice(CsvInvoiceRecord item);

    @InheritInverseConfiguration(name = "toInvoice")
//    @Mapping(target = "id", expression = "java(fromID(invoice.getID()))")
//    @Mapping(target = "customizationID", expression = "java(fromCustomizationID(invoice.getCustomizationID()))")
//    @Mapping(target = "profileID", expression = "java(fromProfileID(invoice.getProfileID()))")
    @Mapping(target = "invoiceTypeCode", expression = "java(fromInvoiceTypeCode(invoice.getInvoiceTypeCode()))")
    @Mapping(target = "issueDate", expression = "java(fromIssueDate(invoice.getIssueDate()))")
    @Mapping(target = "dueDate", expression = "java(fromDueDate(invoice.getDueDate()))")
    @Mapping(target = "taxPointDate", expression = "java(fromTaxPointDate(invoice.getTaxPointDate()))")
    @Mapping(target = "documentCurrencyCode", expression = "java(fromDocumentCurrencyCode(invoice.getDocumentCurrencyCode()))")
    @Mapping(target = "taxCurrencyCode", expression = "java(fromTaxCurrencyCode(invoice.getTaxCurrencyCode()))")
    @Mapping(target = "accountingCost", expression = "java(fromAccountingCost(invoice.getAccountingCost()))")
    @Mapping(target = "buyerReference", expression = "java(fromBuyerReference(invoice.getBuyerReference()))")
    @Mapping(target = "invoicePeriodStartDate", expression = "java(fromInvoicePeriodStart(invoice.getInvoicePeriod()))")
    @Mapping(target = "invoicePeriodEndDate", expression = "java(fromInvoicePeriodEnd(invoice.getInvoicePeriod()))")
    @Mapping(target = "contractDocumentReferenceID", expression = "java(fromContractDocumentReference(invoice.getContractDocumentReference()))")
    @Mapping(target = "additionalDocumentReference1ID", expression = "java(fromAdditionalDocumentReference1ID(invoice.getAdditionalDocumentReference()))")
    @Mapping(target = "additionalDocumentReference1DocumentTypeCode", expression = "java(fromAdditionalDocumentReference1DocumentTypeCode(invoice.getAdditionalDocumentReference()))")
    @Mapping(target = "additionalDocumentReference2ID", expression = "java(fromAdditionalDocumentReference2ID(invoice.getAdditionalDocumentReference()))")
    @Mapping(target = "additionalDocumentReference2DocumentDescription", expression = "java(fromAdditionalDocumentReference2DocumentDescription(invoice.getAdditionalDocumentReference()))")
    @Mapping(target = "additionalDocumentReference2AttachmentURI", expression = "java(fromAdditionalDocumentReference2AttachmentURI(invoice.getAdditionalDocumentReference()))")
    @Mapping(target = "paymentMeans", expression = "java(fromPaymentMeans(invoice.getPaymentMeans()))")
    @Mapping(target = "note", expression = "java(fromNote(invoice.getNote()))")
    CsvInvoiceRecord fromInvoice(InvoiceType invoice);


    InvoiceType documentToInvoice(InvoiceDocument doc);

    @InheritInverseConfiguration(name = "documentToInvoice")
    InvoiceDocument invoiceToDocument(InvoiceType invoice);


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

    default AccountingCostType toAccountingCost(String value) {
        if (value == null) return null;
        AccountingCostType t = new AccountingCostType();
        t.setValue(value);
        return t;
    }

    default BuyerReferenceType toBuyerReference(String value) {
        if (value == null) return null;
        BuyerReferenceType t = new BuyerReferenceType();
        t.setValue(value);
        return t;
    }

    default java.util.List<PeriodType> toInvoicePeriod(CsvInvoiceRecord item) {
        if (item.getInvoicePeriodStartDate() == null && item.getInvoicePeriodEndDate() == null) return new java.util.ArrayList<>();
        PeriodType p = new PeriodType();
        if (item.getInvoicePeriodStartDate() != null) {
            StartDateType s = new StartDateType();
            s.setValue(toXmlDate(item.getInvoicePeriodStartDate()));
            p.setStartDate(s);
        }
        if (item.getInvoicePeriodEndDate() != null) {
            EndDateType e = new EndDateType();
            e.setValue(toXmlDate(item.getInvoicePeriodEndDate()));
            p.setEndDate(e);
        }
        return new java.util.ArrayList<>(java.util.Collections.singletonList(p));
    }

    default java.util.List<DocumentReferenceType> toContractDocumentReference(CsvInvoiceRecord item) {
        if (item.getContractDocumentReferenceID() == null) return new java.util.ArrayList<>();
        DocumentReferenceType ref = new DocumentReferenceType();
        ref.setID(toID(item.getContractDocumentReferenceID()));
        return new java.util.ArrayList<>(java.util.Collections.singletonList(ref));
    }

    default java.util.List<DocumentReferenceType> toAdditionalDocumentReferences(CsvInvoiceRecord item) {
        java.util.List<DocumentReferenceType> list = new java.util.ArrayList<>();
        if (item.getAdditionalDocumentReference1ID() != null) {
            DocumentReferenceType ref1 = new DocumentReferenceType();
            ref1.setID(toID(item.getAdditionalDocumentReference1ID()));
            if (item.getAdditionalDocumentReference1DocumentTypeCode() != null) {
                DocumentTypeCodeType code = new DocumentTypeCodeType();
                code.setValue(item.getAdditionalDocumentReference1DocumentTypeCode());
                ref1.setDocumentTypeCode(code);
            }
            list.add(ref1);
        }
        if (item.getAdditionalDocumentReference2ID() != null) {
            DocumentReferenceType ref2 = new DocumentReferenceType();
            ref2.setID(toID(item.getAdditionalDocumentReference2ID()));
            if (item.getAdditionalDocumentReference2DocumentDescription() != null) {
                DocumentDescriptionType desc = new DocumentDescriptionType();
                desc.setValue(item.getAdditionalDocumentReference2DocumentDescription());
                ref2.getDocumentDescription().add(desc);
            }
            if (item.getAdditionalDocumentReference2AttachmentURI() != null) {
                AttachmentType att = new AttachmentType();
                ExternalReferenceType ext = new ExternalReferenceType();
                URIType uri = new URIType();
                uri.setValue(item.getAdditionalDocumentReference2AttachmentURI());
                ext.setURI(uri);
                att.setExternalReference(ext);
                ref2.setAttachment(att);
            }
            list.add(ref2);
        }
        return list;
    }

    default java.util.List<PaymentMeansType> toPaymentMeans(CsvInvoiceRecord item) {
        // Some CSV formats use a flat "paymentMeans" column representing the
        // PaymentMeansCode. Prefer that if present, otherwise fall back to the
        // individual PaymentMeans_* fields.
        if (item.getPaymentMeans() != null && !item.getPaymentMeans().isBlank()) {
            PaymentMeansType pm = new PaymentMeansType();
            PaymentMeansCodeType code = new PaymentMeansCodeType();
            code.setValue(item.getPaymentMeans());
            pm.setPaymentMeansCode(code);
            return new java.util.ArrayList<>(java.util.Collections.singletonList(pm));
        }

        if (item.getPaymentMeansPaymentMeansCode() == null) return new java.util.ArrayList<>();
        PaymentMeansType pm = new PaymentMeansType();
        PaymentMeansCodeType code = new PaymentMeansCodeType();
        code.setValue(item.getPaymentMeansPaymentMeansCode());
        pm.setPaymentMeansCode(code);
        if (item.getPaymentMeansPaymentID() != null) {
            PaymentIDType pid = new PaymentIDType();
            pid.setValue(item.getPaymentMeansPaymentID());
            pm.getPaymentID().add(pid);
        }
        if (item.getPaymentMeansPayeeFinancialAccountID() != null) {
            FinancialAccountType acct = new FinancialAccountType();
            acct.setID(toID(item.getPaymentMeansPayeeFinancialAccountID()));
            if (item.getPaymentMeansPayeeFinancialAccountName() != null) {
                NameType name = new NameType();
                name.setValue(item.getPaymentMeansPayeeFinancialAccountName());
                acct.setName(name);
            }
            if (item.getPaymentMeansPayeeFinancialAccountBranchID() != null) {
                BranchType br = new BranchType();
                br.setID(toID(item.getPaymentMeansPayeeFinancialAccountBranchID()));
                acct.setFinancialInstitutionBranch(br);
            }
            pm.setPayeeFinancialAccount(acct);
        }
        return new java.util.ArrayList<>(java.util.Collections.singletonList(pm));
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

    // -----------------------------------------------------------------
    // Inverse mapping helpers converting from UBL types to Strings
    // -----------------------------------------------------------------

    default String fromID(IDType id) {
        return id == null ? null : id.getValue();
    }

    default String fromCustomizationID(CustomizationIDType id) {
        return id == null ? null : id.getValue();
    }

    default String fromProfileID(ProfileIDType id) {
        return id == null ? null : id.getValue();
    }

    default String fromInvoiceTypeCode(InvoiceTypeCodeType type) {
        return type == null ? null : type.getValue();
    }

    default String fromNote(java.util.List<NoteType> notes) {
        if (notes == null || notes.isEmpty()) return null;
        NoteType n = notes.get(0);
        return n == null ? null : n.getValue();
    }

    default String fromIssueDate(IssueDateType d) {
        return d == null || d.getValue() == null ? null : d.getValue().toString();
    }

    default String fromDueDate(DueDateType d) {
        return d == null || d.getValue() == null ? null : d.getValue().toString();
    }

    default String fromTaxPointDate(TaxPointDateType d) {
        return d == null || d.getValue() == null ? null : d.getValue().toString();
    }

    default String fromDocumentCurrencyCode(DocumentCurrencyCodeType t) {
        return t == null ? null : t.getValue();
    }

    default String fromTaxCurrencyCode(TaxCurrencyCodeType t) {
        return t == null ? null : t.getValue();
    }

    default String fromAccountingCost(AccountingCostType t) {
        return t == null ? null : t.getValue();
    }

    default String fromBuyerReference(BuyerReferenceType t) {
        return t == null ? null : t.getValue();
    }

    default String fromInvoicePeriodStart(java.util.List<PeriodType> list) {
        if (list == null || list.isEmpty()) return null;
        StartDateType s = list.get(0).getStartDate();
        return s == null || s.getValue() == null ? null : s.getValue().toString();
    }

    default String fromInvoicePeriodEnd(java.util.List<PeriodType> list) {
        if (list == null || list.isEmpty()) return null;
        EndDateType e = list.get(0).getEndDate();
        return e == null || e.getValue() == null ? null : e.getValue().toString();
    }

    default String fromContractDocumentReference(java.util.List<DocumentReferenceType> refs) {
        if (refs == null || refs.isEmpty() || refs.get(0).getID() == null) return null;
        return fromID(refs.get(0).getID());
    }

    default String fromAdditionalDocumentReference1ID(java.util.List<DocumentReferenceType> refs) {
        if (refs == null || refs.isEmpty() || refs.get(0).getID() == null) return null;
        return fromID(refs.get(0).getID());
    }

    default String fromAdditionalDocumentReference1DocumentTypeCode(java.util.List<DocumentReferenceType> refs) {
        if (refs == null || refs.isEmpty()) return null;
        DocumentReferenceType r = refs.get(0);
        return r.getDocumentTypeCode() != null ? r.getDocumentTypeCode().getValue() : null;
    }

    default String fromAdditionalDocumentReference2ID(java.util.List<DocumentReferenceType> refs) {
        if (refs == null || refs.size() < 2 || refs.get(1).getID() == null) return null;
        return fromID(refs.get(1).getID());
    }

    default String fromAdditionalDocumentReference2DocumentDescription(java.util.List<DocumentReferenceType> refs) {
        if (refs == null || refs.size() < 2) return null;
        DocumentReferenceType r = refs.get(1);
        return r.getDocumentDescription().isEmpty() ? null : r.getDocumentDescription().get(0).getValue();
    }

    default String fromAdditionalDocumentReference2AttachmentURI(java.util.List<DocumentReferenceType> refs) {
        if (refs == null || refs.size() < 2) return null;
        DocumentReferenceType r = refs.get(1);
        return r.getAttachment() != null && r.getAttachment().getExternalReference() != null
                && r.getAttachment().getExternalReference().getURI() != null
                ? r.getAttachment().getExternalReference().getURI().getValue()
                : null;
    }



    /** Map a list of PaymentMeans to the first PaymentMeansCode value. */
    default String fromPaymentMeans(java.util.List<PaymentMeansType> list) {
        if (list == null || list.isEmpty()) return null;
        PaymentMeansType pm = list.get(0);
        return pm.getPaymentMeansCode() != null ? pm.getPaymentMeansCode().getValue() : null;
    }

    /** Create a PaymentMeans list from a single PaymentMeansCode string. */
    default java.util.List<PaymentMeansType> toPaymentMeans(String codeValue) {
        if (codeValue == null) return null;
        PaymentMeansType pm = new PaymentMeansType();
        PaymentMeansCodeType code = new PaymentMeansCodeType();
        code.setValue(codeValue);
        pm.setPaymentMeansCode(code);
        java.util.List<PaymentMeansType> list = new java.util.ArrayList<>();
        list.add(pm);
        return list;
    }

    // -----------------------------------------------------------------
    // Additional converters for InvoiceDocument mapping
    // -----------------------------------------------------------------

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

    default RegistrationNameType toRegistrationName(String value) {
        if (value == null) return null;
        RegistrationNameType t = new RegistrationNameType();
        t.setValue(value);
        return t;
    }

    default String fromRegistrationName(RegistrationNameType t) {
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

}
