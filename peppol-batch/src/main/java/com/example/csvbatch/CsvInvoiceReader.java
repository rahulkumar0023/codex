package com.example.csvbatch;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DefaultFieldSetFactory;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

import static com.example.csvbatch.CsvFieldNames.INVOICE_FIELDS;

public class CsvInvoiceReader extends FlatFileItemReader<CsvInvoiceDto>
        implements ResourceAwareItemReaderItemStream<CsvInvoiceDto> {

    public CsvInvoiceReader() {
        setLinesToSkip(1);
        setName("csvInvoiceReader");

        setLineMapper(createLineMapper());
    }

    private LineMapper<CsvInvoiceDto> createLineMapper() {
        DefaultLineMapper<CsvInvoiceDto> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(";");
        tokenizer.setNames(INVOICE_FIELDS); // Constant array of field names
        tokenizer.setFieldSetFactory(new DefaultFieldSetFactory());

        BeanWrapperFieldSetMapper<CsvInvoiceDto> mapper = new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(CsvInvoiceDto.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(mapper);

        return lineMapper;
    }
}
