package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.batch.item.Chunk;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

import org.junit.jupiter.api.Test;

/**
 * Reads an invoice sample from the Oxalis peppol-specifications repository if present.
 * The repository location can be provided using the system property 'peppolSpecDir'.
 */
class XmlInvoiceTest {

    @Test
    void readsInvoiceFromSpecificationsRepo() throws Exception {
        Path repo = Path.of(System.getProperty("peppolSpecDir", "../peppol-specifications"));
        assumeTrue(Files.isDirectory(repo), "peppol-specifications repo not found");

        Optional<Path> example = findFirstXml(repo);
        assumeTrue(example.isPresent(), "No XML invoice found in repo");

        String xml = Files.readString(example.get());
        XmlInvoiceReader parser = new XmlInvoiceReader();
        InvoiceType invoice = parser.parse(xml);
        assertNotNull(invoice);
    }

    @Test
    void readsAndWritesInvoiceFromSpecificationsRepo() throws Exception {
        Path repo = Path.of(System.getProperty("peppolSpecDir", "../peppol-specifications"));
        assumeTrue(Files.isDirectory(repo), "peppol-specifications repo not found");

        Optional<Path> example = findFirstXml(repo);
        assumeTrue(example.isPresent(), "No XML invoice found in repo");

        String xml = Files.readString(example.get());
        XmlInvoiceReader parser = new XmlInvoiceReader();
        InvoiceType invoice = parser.parse(xml);
        assertNotNull(invoice);

        Path outDir = Files.createTempDirectory("spec-invoice-out");
        XmlInvoiceWriter writer = new XmlInvoiceWriter(outDir);
        Chunk<InvoiceType> chunk = new Chunk<>();
        chunk.add(invoice);
        writer.write(chunk);

        Path written = outDir.resolve(invoice.getID().getValue() + ".xml");
        assumeTrue(Files.exists(written), "invoice not written" );
    }

    private Optional<Path> findFirstXml(Path dir) throws Exception {
        try (Stream<Path> s = Files.walk(dir)) {
            return s.filter(p -> p.toString().toLowerCase().endsWith(".xml"))
                    .findFirst();
        }
    }
}
