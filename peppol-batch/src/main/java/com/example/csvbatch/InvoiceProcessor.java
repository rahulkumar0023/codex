package com.example.csvbatch;

import lombok.RequiredArgsConstructor;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoiceProcessor implements ItemProcessor<CsvInvoiceDto, InvoiceType> {

    private final CsvInvoiceMapper mapper;

    @Override
    public InvoiceType process(CsvInvoiceDto dto) {
        return mapper.toInvoiceType(dto);
    }
}
