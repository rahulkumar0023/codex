package com.example.peppol.batch;

import java.nio.file.Path;

import network.oxalis.peppol.ubl2.jaxb.CreditNoteType;

/**
 * Holds a parsed UBL {@link CreditNoteType} along with its source file path.
 */
public class CreditNoteRecord {
    private final CreditNoteType creditNote;
    private final Path sourceFile;

    public CreditNoteRecord(CreditNoteType creditNote, Path sourceFile) {
        this.creditNote = creditNote;
        this.sourceFile = sourceFile;
    }

    public CreditNoteType getCreditNote() {
        return creditNote;
    }

    public Path getSourceFile() {
        return sourceFile;
    }
}
