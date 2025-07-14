package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.core.io.FileSystemResource;

import com.example.peppol.batch.csv.CsvInvoiceReader;
import com.example.peppol.batch.csv.CsvInvoiceProcessor;
import com.example.peppol.batch.csv.CsvInvoiceRecord;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

/**
 * Integration test converting a CSV invoice row into an XML invoice.
 */
class CsvInvoiceXmlGenerationTest {

    @Test
    void generatesInvoiceXmlFromCsv() throws Exception {
        CsvInvoiceReader reader = new CsvInvoiceReader();
        reader.setResource(new FileSystemResource(Path.of("src/test/resources/sample-invoice.csv")));

        StepExecution stepExecution = new StepExecution("csv", new JobExecution(1L));
        StepSynchronizationManager.register(stepExecution);
        reader.open(stepExecution.getExecutionContext());
        CsvInvoiceRecord record = reader.read();
        reader.close();
        StepSynchronizationManager.close();

        CsvInvoiceProcessor processor = new CsvInvoiceProcessor();
        InvoiceType invoice = processor.process(record);

        XmlInvoiceWriter writer = new XmlInvoiceWriter();
        String xml = writer.writeToString(invoice);

        Path outFile = Files.createTempDirectory("csv-to-xml").resolve(
                invoice.getID().getValue() + ".xml");
        writer.write(invoice, outFile);

        assertNotNull(xml);
        assertTrue(xml.contains("<Invoice"));
        assertTrue(Files.exists(outFile));

        String writtenXml = Files.readString(outFile);
        XmlInvoiceReader parser = new XmlInvoiceReader();
        InvoiceType parsed = parser.parse(writtenXml);
        assertEquals(invoice.getID().getValue(), parsed.getID().getValue());
    }
}
