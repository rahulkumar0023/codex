package com.example.peppol.batch.csv;

import org.springframework.batch.item.ItemProcessor;
import lombok.extern.slf4j.Slf4j;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import com.example.peppol.batch.csv.CsvInvoiceMapper;

/**
 * Converts {@link CsvInvoiceRecord} instances into UBL {@link InvoiceType} objects
 * using MapStruct for field mapping.
 */
@Slf4j
public class CsvInvoiceProcessor implements ItemProcessor<CsvInvoiceRecord, InvoiceType> {

    private final CsvInvoiceMapper mapper = CsvInvoiceMapper.INSTANCE;

    @Override
    public InvoiceType process(CsvInvoiceRecord item) {
        InvoiceType invoice = mapper.toInvoice(item);
        log.debug("Mapped CSV record {} to invoice {}", item.getId(), invoice.getID() != null ? invoice.getID().getValue() : null);
        return invoice;
    }
}
