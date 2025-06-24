package com.example.peppol.batch;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemReader;

/**
 * Reads XML invoice files from a directory.
 */
public class InvoiceXmlFileReader implements ItemReader<InvoiceDocument> {

    private final Iterator<Path> fileIterator;

    public InvoiceXmlFileReader(Path inputDir) {
        try {
            List<Path> files = Files.list(inputDir)
                .filter(p -> p.toString().toLowerCase().endsWith(".xml"))
                .collect(Collectors.toList());
            this.fileIterator = files.iterator();
        } catch (IOException e) {
            throw new RuntimeException("Failed to list XML files", e);
        }
    }

    @Override
    public InvoiceDocument read() throws Exception {
        if (!fileIterator.hasNext()) {
            return null;
        }
        Path file = fileIterator.next();
        String xml = Files.readString(file, StandardCharsets.UTF_8);
        return new InvoiceDocument(xml, file);
    }
}
