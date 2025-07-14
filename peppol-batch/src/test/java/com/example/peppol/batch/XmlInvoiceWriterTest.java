package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.core.io.FileSystemResource;

import com.example.peppol.batch.csv.CsvInvoiceReader;
import com.example.peppol.batch.csv.CsvInvoiceRecord;
import com.example.peppol.batch.csv.CsvInvoiceProcessor;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

@Slf4j
class XmlInvoiceWriterTest {

    @Test
    void writesInvoiceToXmlString() throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/complex-invoice.xml"));
        XmlInvoiceReader parser = new XmlInvoiceReader();
        InvoiceType invoice = parser.parse(xml);

        XmlInvoiceWriter writer = new XmlInvoiceWriter();
        String out = writer.writeToString(invoice);

        Path outFile = Path.of("target", "generated-invoice.xml");
        Files.createDirectories(outFile.getParent());
        writer.write(invoice, outFile);
        log.info("Written invoice to {}", outFile.toAbsolutePath());

        assertNotNull(out);
        assertFalse(out.isEmpty());

        InvoiceType parsed = parser.parse(out);

        log.info("parsed.getID().getValue(): {}", parsed.getID().getValue());
        assertEquals(invoice.getID().getValue(), parsed.getID().getValue());
        assertTrue(out.contains("<Invoice xmlns=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2\""));
        assertFalse(out.contains("CommonExtensionComponents-2"));
    }

    @Test
    void writesInvoiceUsingItemWriter() throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/complex-invoice.xml"));
        XmlInvoiceReader parser = new XmlInvoiceReader();
        InvoiceType invoice = parser.parse(xml);

        Path outputDir = Files.createTempDirectory("invoice-item-writer");
        XmlInvoiceWriter writer = new XmlInvoiceWriter(outputDir);
        Chunk<InvoiceType> chunk = new Chunk<>();
        chunk.add(invoice);
        writer.write(chunk);

        Path written = outputDir.resolve(invoice.getID().getValue() + ".xml");
        assertTrue(Files.exists(written));

        String writtenXml = Files.readString(written);
        InvoiceType parsed = parser.parse(writtenXml);
        assertEquals(invoice.getID().getValue(), parsed.getID().getValue());
    }

    @Test
    void readsFromCsvAndWritesXml() throws Exception {
        CsvInvoiceReader csvReader = new CsvInvoiceReader();
        csvReader.setResource(new FileSystemResource(Path.of("src/test/resources/sample-invoice.csv")));

        StepExecution stepExecution = new StepExecution("csv", new JobExecution(1L));
        StepSynchronizationManager.register(stepExecution);
        csvReader.open(stepExecution.getExecutionContext());
        CsvInvoiceRecord record = csvReader.read();
        csvReader.close();
        StepSynchronizationManager.close();

        CsvInvoiceProcessor processor = new CsvInvoiceProcessor();
        InvoiceType invoice = processor.process(record);

        Path outputDir = Files.createTempDirectory("csv-invoice-writer");
        log.info("Written csv invoice to {}", outputDir.toAbsolutePath());
        XmlInvoiceWriter writer = new XmlInvoiceWriter(outputDir);
        Chunk<InvoiceType> chunk = new Chunk<>();
        chunk.add(invoice);
        writer.write(chunk);

        Path written = outputDir.resolve(invoice.getID().getValue() + ".xml");
        assertTrue(Files.exists(written));

        String writtenXml = Files.readString(written);
        XmlInvoiceReader parser = new XmlInvoiceReader();
        InvoiceType parsed = parser.parse(writtenXml);
        assertEquals(invoice.getID().getValue(), parsed.getID().getValue());
    }

}
