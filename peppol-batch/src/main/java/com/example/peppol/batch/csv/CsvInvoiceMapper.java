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

import network.oxalis.peppol.ubl2.jaxb.ecdt.UBLExtensionsType;

/**
 * MapStruct mapper converting {@link CsvInvoiceRecord} instances to
 * {@link InvoiceType}.
 */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CsvInvoiceMapper {

    @Mapping(target = "ID", expression = "java(toID(item.getId()))")
    @Mapping(target = "customizationID", expression = "java(toCustomizationID(item.getCustomizationID()))")
    @Mapping(target = "profileID", expression = "java(toProfileID(item.getProfileID()))")
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

    default java.util.List<PaymentTermsType> toPaymentTerms(CsvInvoiceRecord item) {
        if (item.getPaymentTermsNote() == null) return new java.util.ArrayList<>();
        PaymentTermsType pt = new PaymentTermsType();
        NoteType note = new NoteType();
        note.setValue(item.getPaymentTermsNote());
        pt.getNote().add(note);
        return new java.util.ArrayList<>(java.util.Collections.singletonList(pt));
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

}
