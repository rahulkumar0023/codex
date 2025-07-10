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

    private java.util.List<String> parseLine(String line) {
        java.util.List<String> result = new java.util.ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ';' && !inQuotes) {
                result.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        result.add(sb.toString());
        return result;
    }

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
            try {
                reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                log.error("Failed to open resource {}", resource, e);
                throw new ItemStreamException("Failed to open resource " + resource, e);
            }
        }
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (!headerParsed) {
                    headerParsed = true;
                    headers = parseLine(line);
                    continue;
                }
                if (line.isBlank()) {
                    continue;
                }
                java.util.List<String> parts = parseLine(line);
                java.util.Map<String,String> map = new java.util.LinkedHashMap<>();
                for (int i = 0; i < headers.size() && i < parts.size(); i++) {
                    map.put(headers.get(i), parts.get(i));
                }
                CsvInvoiceRecord rec = CsvInvoiceRecord.builder()
                        .fields(map)
                        .build();
                log.debug("Read record: {}", rec);
                return rec;
            }
        } catch (IOException e) {
            log.error("Failed to read CSV from {}", resource, e);
            throw new ItemStreamException("Failed to read CSV", e);
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
                log.warn("Error closing reader for {}", resource, e);
                throw new ItemStreamException("Failed to close reader", e);
            } finally {
                reader = null;
            }
        }
    }
}
