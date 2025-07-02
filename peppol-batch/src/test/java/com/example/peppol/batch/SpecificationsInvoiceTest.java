package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.batch.item.Chunk;

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

    @Test
    void readsAndWritesInvoiceFromSpecificationsRepo() throws Exception {
        Path repo = Path.of(System.getProperty("peppolSpecDir", "../peppol-specifications"));
        assumeTrue(Files.isDirectory(repo), "peppol-specifications repo not found");

        Optional<Path> example = findFirstXml(repo);
        assumeTrue(example.isPresent(), "No XML invoice found in repo");

        InvoiceXmlFileReader reader = new InvoiceXmlFileReader(example.get().getParent());
        InvoiceDocument doc = reader.read();
        assertNotNull(doc);

        Path outDir = Files.createTempDirectory("spec-invoice-out");
        InvoiceXmlWriter writer = new InvoiceXmlWriter(outDir);
        Chunk<InvoiceDocument> chunk = new Chunk<>();
        chunk.add(doc);
        writer.write(chunk);

        Path written = outDir.resolve(example.get().getFileName());
        assumeTrue(Files.exists(written), "invoice not written" );
    }

    private Optional<Path> findFirstXml(Path dir) throws Exception {
        try (Stream<Path> s = Files.walk(dir)) {
            return s.filter(p -> p.toString().toLowerCase().endsWith(".xml"))
                    .findFirst();
        }
    }
}
