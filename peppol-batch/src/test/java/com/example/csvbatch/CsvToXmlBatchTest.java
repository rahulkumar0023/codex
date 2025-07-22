package com.example.csvbatch;


import com.example.csvbatch.config.BatchConfig;
import com.example.csvbatch.mapper.CsvInvoiceMapper;
import com.example.csvbatch.model.CsvInvoiceDto;
import com.example.csvbatch.writer.XmlInvoiceWriter;
import lombok.extern.slf4j.Slf4j;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class CsvToXmlBatchTest {

    @Autowired
    CsvInvoiceMapper mapper;

    @Autowired
    BatchConfig csvReaderConfig;

    @Test
    void shouldConvertCsvRowsToXmlFiles() throws Exception {
        FlatFileItemReader<CsvInvoiceDto> reader = csvReaderConfig.reader();
        reader.open(new ExecutionContext());

        Path outputDir = Path.of("target/test-xml-out");
        Files.createDirectories(outputDir);
        XmlInvoiceWriter writer = new XmlInvoiceWriter(outputDir);

        CsvInvoiceDto dto;
        int count = 0;
        while ((dto = reader.read()) != null) {
            InvoiceType invoice = mapper.toInvoiceType(dto);
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
