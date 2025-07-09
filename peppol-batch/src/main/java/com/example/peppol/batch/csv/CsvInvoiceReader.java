package com.example.peppol.batch.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

/**
 * Simple CSV reader that converts each line into a {@link CsvInvoiceRecord}.
 */
@Slf4j
public class CsvInvoiceReader implements ResourceAwareItemReaderItemStream<CsvInvoiceRecord> {

    private Resource resource;
    private BufferedReader reader;
    private boolean headerParsed;
    private java.util.List<String> headers;

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
        this.headerParsed = false;
        this.headers = null;
    }

    @Override
    public CsvInvoiceRecord read() throws Exception {
        if (resource == null) {
            return null;
        }
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
        }
        String line;
        while ((line = reader.readLine()) != null) {
            if (!headerParsed) {
                headerParsed = true;
                headers = java.util.Arrays.asList(line.split(";", -1));
                continue;
            }
            if (line.isBlank()) {
                continue;
            }
            String[] parts = line.split(";", -1);
            java.util.Map<String,String> map = new java.util.LinkedHashMap<>();
            for (int i = 0; i < headers.size() && i < parts.length; i++) {
                map.put(headers.get(i), parts[i]);
            }
            CsvInvoiceRecord rec = CsvInvoiceRecord.builder()
                    .fields(map)
                    .build();
            log.debug("Read record: {}", rec);
            return rec;
        }
        return null;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        // nothing to open
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // no state
    }

    @Override
    public void close() throws ItemStreamException {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                // ignore
            }
            reader = null;
        }
    }
}
