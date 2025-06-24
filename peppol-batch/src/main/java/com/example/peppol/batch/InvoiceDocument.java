package com.example.peppol.batch;

import java.nio.file.Path;

/**
 * Simple holder for invoice XML and output file name.
 */
public class InvoiceDocument {
    private final String xml;
    private final Path sourceFile;

    public InvoiceDocument(String xml, Path sourceFile) {
        this.xml = xml;
        this.sourceFile = sourceFile;
    }

    public String getXml() {
        return xml;
    }

    public Path getSourceFile() {
        return sourceFile;
    }
}
