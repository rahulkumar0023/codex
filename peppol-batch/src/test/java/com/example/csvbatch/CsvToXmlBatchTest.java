package com.example.csvbatch;


import lombok.extern.slf4j.Slf4j;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class CsvToXmlBatchTest {

    @Autowired
    private MultiResourceItemReader<CsvInvoiceDto> reader;

    @Autowired
    private CsvInvoiceMapper csvInvoiceMapper;

    @Test
    void shouldConvertCsvRowsToXmlFiles() throws Exception {
        reader.open(new ExecutionContext());

        Path outputDir = Path.of("target/test-xml-out");
        Files.createDirectories(outputDir);
        XmlInvoiceWriter writer = new XmlInvoiceWriter(outputDir);

        CsvInvoiceDto dto;
        int count = 0;
        while ((dto = reader.read()) != null) {
            InvoiceType invoice = csvInvoiceMapper.toInvoiceType(dto);
            Path outFile = outputDir.resolve(dto.getInvoiceNumber() + ".xml");
            writer.write(invoice, outFile);
            assertThat(Files.exists(outFile)).isTrue();
            log.info("Generated XML at: {}", outFile);
            count++;
        }

        reader.close();
        assertThat(count).isGreaterThan(0);
    }
}
