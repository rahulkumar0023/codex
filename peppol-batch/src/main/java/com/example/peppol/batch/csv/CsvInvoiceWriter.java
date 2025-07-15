package com.example.peppol.batch.csv;

import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.io.Writer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Simple writer that marshals {@link CsvInvoiceRecord} instances to CSV files
 * using OpenCSV.
 */
public class CsvInvoiceWriter implements ItemWriter<CsvInvoiceRecord> {

    private final Path outputDir;

    /** Create a writer without an output directory. */
    public CsvInvoiceWriter() {
        this.outputDir = null;
    }

    /**
     * Create a writer that outputs CSV invoices to the given directory.
     * @param outputDir target directory
     */
    public CsvInvoiceWriter(Path outputDir) {
        this.outputDir = outputDir;
        try {
            Files.createDirectories(outputDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create output directory", e);
        }
    }

    /** Write the record to the given file path. */
    public void write(CsvInvoiceRecord record, Path output) throws Exception {
        HeaderColumnNameMappingStrategy<CsvInvoiceRecord> strategy =
                new HeaderColumnNameMappingStrategy<>();
        strategy.setType(CsvInvoiceRecord.class);
        try (Writer writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) {
            StatefulBeanToCsv<CsvInvoiceRecord> bean =
                    new StatefulBeanToCsvBuilder<CsvInvoiceRecord>(writer)
                            .withSeparator(';')
                            .withQuotechar('"')
                            .withMappingStrategy(strategy)
                            .build();
            bean.write(record);
        }
    }

    @Override
    public void write(Chunk<? extends CsvInvoiceRecord> items) throws Exception {
        if (outputDir == null) {
            throw new IllegalStateException("Output directory not configured");
        }
        int counter = 0;
        for (CsvInvoiceRecord rec : items) {
            String base = (rec.getId() != null && !rec.getId().isBlank())
                    ? rec.getId() : "invoice-" + (++counter);
            Path out = outputDir.resolve(base + ".csv");
            write(rec, out);
        }
    }
}
