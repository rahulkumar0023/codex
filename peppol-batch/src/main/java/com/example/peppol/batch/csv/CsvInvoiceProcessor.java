package com.example.peppol.batch.csv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Converts {@link CsvInvoiceRecord} instances into UBL {@link InvoiceType} objects
 * using MapStruct for field mapping.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CsvInvoiceProcessor implements ItemProcessor<CsvInvoiceRecord, InvoiceType> {

    private final CsvInvoiceMapper mapper;

    /**
     * Default constructor using the MapStruct-generated mapper instance.
     * Needed for tests that instantiate the processor directly.
     */
    public CsvInvoiceProcessor() {
        this(Mappers.getMapper(CsvInvoiceMapper.class));
    }

    @Override
    public InvoiceType process(CsvInvoiceRecord item) {
        InvoiceType invoice = mapper.toInvoice(item);
        log.debug("Mapped CSV record {} to invoice {}", item.getNote(), invoice.getNote() != null ? invoice.getNote() : null);
        return invoice;
    }
}
