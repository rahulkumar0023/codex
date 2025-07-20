package com.example.peppol.batch.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Utility for reading CSV headers dynamically.
 */
@Component
@Slf4j
public class CsvHeaderReader {

    /**
     * Reads the first line of a CSV file and returns the column names.
     *
     * @param filePath path to the CSV file
     * @return array of header names without quotes
     * @throws IOException if the file cannot be read
     */
    public String[] readHeaders(String filePath) throws IOException {
        log.info("Reading CSV headers from: {}", filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            if (!StringUtils.hasText(headerLine)) {
                throw new IllegalArgumentException("CSV file is empty or has no header");
            }
            String[] headers = Arrays.stream(headerLine.split(";"))
                    .map(h -> h.replace("\"", "").trim())
                    .filter(StringUtils::hasText)
                    .toArray(String[]::new);
            log.info("Found {} headers", headers.length);
            return headers;
        }
    }

    /**
     * Validates that the given headers contain all required names.
     *
     * @param headers         parsed headers
     * @param requiredHeaders required header names
     * @return {@code true} if all required headers are present
     */
    public boolean validateHeaders(String[] headers, String[] requiredHeaders) {
        var headerSet = Arrays.stream(headers)
                .map(String::toLowerCase)
                .collect(java.util.stream.Collectors.toSet());
        return Arrays.stream(requiredHeaders)
                .map(String::toLowerCase)
                .allMatch(headerSet::contains);
    }
}
