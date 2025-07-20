package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.example.peppol.batch.util.CsvHeaderReader;

class CsvHeaderReaderTest {

    @Test
    void readsAndValidatesHeaders() throws Exception {
        CsvHeaderReader reader = new CsvHeaderReader();
        String csv = Path.of("src/test/resources/sample-invoice.csv").toString();

        String[] headers = reader.readHeaders(csv);
        assertEquals(65, headers.length);
        assertTrue(reader.validateHeaders(headers, new String[] {"CustomizationID", "ID", "IssueDate"}));
    }
}
