package com.example.csvbatch;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DefaultFieldSetFactory;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CsvInvoiceReader extends FlatFileItemReader<CsvInvoiceDto>
        implements ResourceAwareItemReaderItemStream<CsvInvoiceDto> {

    @Override
    public void setResource(Resource resource) {
        super.setResource(resource);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IllegalStateException("CSV file has no header line: " + resource.getFilename());
            }

            String[] headerFields = headerLine.split(";");
            setLinesToSkip(1);
            setName("csvInvoiceReader");

            DefaultLineMapper<CsvInvoiceDto> lineMapper = new DefaultLineMapper<>();
            DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(";");
            tokenizer.setNames(headerFields);
            tokenizer.setStrict(false);
            lineMapper.setLineTokenizer(tokenizer);
            lineMapper.setFieldSetMapper(new SmartCsvInvoiceFieldSetMapper());

            setLineMapper(lineMapper);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize CsvInvoiceReader for " + resource.getFilename(), e);
        }
    }
}