package com.example.peppol.batch;

import java.nio.file.Path;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

/**
 * Holds a parsed UBL {@link InvoiceType} along with its source file path.
 */
public class InvoiceRecord {
    private final InvoiceType invoice;
    private final Path sourceFile;

    public InvoiceRecord(InvoiceType invoice, Path sourceFile) {
        this.invoice = invoice;
        this.sourceFile = sourceFile;
    }

    public InvoiceType getInvoice() {
        return invoice;
    }

    public Path getSourceFile() {
        return sourceFile;
    }
}
