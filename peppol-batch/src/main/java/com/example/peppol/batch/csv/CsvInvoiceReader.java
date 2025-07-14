package com.example.peppol.batch.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * Reader converting CSV rows into {@link CsvInvoiceRecord} instances using OpenCSV.
 */
@Slf4j
public class CsvInvoiceReader implements ResourceAwareItemReaderItemStream<CsvInvoiceRecord> {

    private Resource resource;
    private BufferedReader reader;
    private Iterator<CsvInvoiceRecord> iterator;

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
        this.iterator = null;
    }

    @Override
    public CsvInvoiceRecord read() throws Exception {
        if (resource == null) {
            return null;
        }
        if (iterator == null) {
            try {
                reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                iterator = new CsvToBeanBuilder<CsvInvoiceRecord>(reader)
                        .withType(CsvInvoiceRecord.class)
                        .withSeparator(';')
                        .build()
                        .iterator();
            } catch (IOException e) {
                log.error("Failed to open resource {}", resource, e);
                throw new ItemStreamException("Failed to open resource " + resource, e);
            }
        }
        if (iterator.hasNext()) {
            CsvInvoiceRecord rec = iterator.next();
            log.debug("Read record: {}", rec);
            return rec;
        }
        return null;
    }

    @Override
    public void open(ExecutionContext executionContext) {
        // nothing to do
    }

    @Override
    public void update(ExecutionContext executionContext) {
        // no state
    }

    @Override
    public void close() throws ItemStreamException {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                log.warn("Error closing reader for {}", resource, e);
                throw new ItemStreamException("Failed to close reader", e);
            } finally {
                reader = null;
                iterator = null;
            }
        }
    }
}
