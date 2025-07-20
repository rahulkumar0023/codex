package com.example.csvbatch.processor;

import com.example.csvbatch.mapper.CsvInvoiceMapper;
import com.example.csvbatch.model.CsvInvoiceDto;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class InvoiceItemProcessor implements ItemProcessor<CsvInvoiceDto, InvoiceType> {

    private final CsvInvoiceMapper mapper;

    public InvoiceItemProcessor(CsvInvoiceMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public InvoiceType process(CsvInvoiceDto item) {
        if (!StringUtils.hasText(item.getInvoiceNumber())) {
            throw new IllegalArgumentException("Invoice number missing");
        }
        return mapper.toInvoice(item);
    }
}
