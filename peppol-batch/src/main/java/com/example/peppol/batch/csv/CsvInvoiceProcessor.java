package com.example.peppol.batch.csv;

import org.springframework.batch.item.ItemProcessor;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import com.example.peppol.batch.csv.CsvInvoiceMapper;

/**
 * Converts {@link CsvInvoiceRecord} instances into UBL {@link InvoiceType} objects
 * using MapStruct for field mapping.
 */
public class CsvInvoiceProcessor implements ItemProcessor<CsvInvoiceRecord, InvoiceType> {

    private final CsvInvoiceMapper mapper = CsvInvoiceMapper.INSTANCE;

    @Override
    public InvoiceType process(CsvInvoiceRecord item) {
        return mapper.toInvoice(item);
    }
}
