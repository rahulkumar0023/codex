package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * Reads an invoice sample from the Oxalis peppol-specifications repository if present.
 * The repository location can be provided using the system property 'peppolSpecDir'.
 */
class SpecificationsInvoiceTest {

    @Test
    void readsInvoiceFromSpecificationsRepo() throws Exception {
        Path repo = Path.of(System.getProperty("peppolSpecDir", "../peppol-specifications"));
        assumeTrue(Files.isDirectory(repo), "peppol-specifications repo not found");

        Optional<Path> example = findFirstXml(repo);
        assumeTrue(example.isPresent(), "No XML invoice found in repo");

        InvoiceXmlFileReader reader = new InvoiceXmlFileReader(example.get().getParent());
        InvoiceDocument doc = reader.read();
        assertNotNull(doc);
    }

    private Optional<Path> findFirstXml(Path dir) throws Exception {
        try (Stream<Path> s = Files.walk(dir)) {
            return s.filter(p -> p.toString().toLowerCase().endsWith(".xml"))
                    .findFirst();
        }
    }
}
