package com.example.peppol.batch;

import java.nio.file.Path;

/**
 * Simple holder for invoice XML and output file name.
 */
public class InvoiceDocument {
    private final String xml;
    private final Path sourcePdf;

    public InvoiceDocument(String xml, Path sourcePdf) {
        this.xml = xml;
        this.sourcePdf = sourcePdf;
    }

    public String getXml() {
        return xml;
    }

    public Path getSourcePdf() {
        return sourcePdf;
    }
}
