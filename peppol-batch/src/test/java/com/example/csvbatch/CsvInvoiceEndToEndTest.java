package com.example.csvbatch;




import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class CsvInvoiceEndToEndTest {

    @Test
    void shouldConvertCsvToXmlFilesWithoutSpringContext() throws Exception {
        // Setup CSV reader
        CsvInvoiceReader delegate = new CsvInvoiceReader();
        delegate.setResource(new FileSystemResource("src/test/resources/invoices.csv"));
        delegate.afterPropertiesSet();

        MultiResourceItemReader<CsvInvoiceDto> reader = new MultiResourceItemReader<>();
        reader.setDelegate(delegate);
        reader.setResources(new Resource[]{new FileSystemResource("input/invoices.csv")});
        //reader.afterPropertiesSet();
        reader.open(new ExecutionContext());

        // Setup mapper and writer

        CsvInvoiceMapper mapper = Mappers.getMapper(CsvInvoiceMapper.class);
        Path outputDir = Path.of("target/test-invoice-xml");
        Files.createDirectories(outputDir);
        XmlInvoiceWriter writer = new XmlInvoiceWriter(outputDir);

        CsvInvoiceDto dto;
        while ((dto = reader.read()) != null) {
            InvoiceType invoice = mapper.toInvoiceType(dto);
            assertNotNull(invoice.getID());

            String filename = invoice.getID().getValue() + ".xml";
            Path outputFile = outputDir.resolve(filename);
            writer.write(invoice, outputFile);
            System.out.println("✔ XML written to: " + outputFile);
            assertTrue(Files.exists(outputFile));
        }

        reader.close();
    }
}
