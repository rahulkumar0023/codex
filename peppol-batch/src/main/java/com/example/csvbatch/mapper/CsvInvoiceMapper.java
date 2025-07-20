package com.example.csvbatch.mapper;

import com.example.csvbatch.model.CsvInvoiceDto;
import network.oxalis.peppol.ubl2.jaxb.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CsvInvoiceMapper {

    @Mapping(target = "id.value", source = "invoiceNumber")
    @Mapping(target = "issueDate.value", expression = "java(parseDate(dto.getIssueDate()))")
    @Mapping(target = "dueDate.value", expression = "java(parseDate(dto.getDueDate()))")
    @Mapping(target = "accountingSupplierParty.party.partyName[0].name.value", source = "supplierName")
    @Mapping(target = "accountingCustomerParty.party.partyName[0].name.value", source = "customerName")
    @Mapping(target = "legalMonetaryTotal.lineExtensionAmount.value", expression = "java(parseAmount(dto.getLineExtensionAmount()))")
    @Mapping(target = "legalMonetaryTotal.lineExtensionAmount.currencyID", source = "currencyId")
    InvoiceType toInvoice(CsvInvoiceDto dto);

    default XMLGregorianCalendarType parseDate(String date) {
        if (date == null || date.isBlank()) return null;
        XMLGregorianCalendarType t = new XMLGregorianCalendarType();
        t.setValue(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return t;
    }

    default BigDecimal parseAmount(String amount) {
        if (amount == null || amount.isBlank()) return null;
        return new BigDecimal(amount);
    }
}
