package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.example.peppol.batch.csv.CsvInvoiceMapper;
import com.example.peppol.batch.csv.CsvInvoiceRecord;
import com.example.peppol.batch.csv.CsvInvoiceWriter;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

/**
 * Integration test converting an XML invoice into a CSV file.
 */
@Slf4j
class XmlInvoiceCsvGenerationTest {

    @Test
    void generatesCsvFromInvoiceXml() throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/complex-invoice.xml"));
        XmlInvoiceReader reader = new XmlInvoiceReader();
        InvoiceType invoice = reader.parse(xml);

        CsvInvoiceMapper mapper = Mappers.getMapper(CsvInvoiceMapper.class);
        CsvInvoiceRecord record = mapper.fromInvoice(invoice);
        assertNotNull(record);

        Path outDir = Files.createTempDirectory("xml-to-csv");
        CsvInvoiceWriter writer = new CsvInvoiceWriter(outDir);
        writer.write(record, outDir.resolve(invoice.getID().getValue() + ".csv"));

        Path written = outDir.resolve(invoice.getID().getValue() + ".csv");
        assertTrue(Files.exists(written));
        log.info("File written to {}", written.toAbsolutePath());
        String csv = Files.readString(written);
        assertTrue(csv.contains("\"" + invoice.getID().getValue() + "\""));
    }
}
