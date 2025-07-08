package com.example.peppol.batch;

import java.nio.file.Path;

import javax.xml.namespace.QName;
import network.oxalis.peppol.ubl2.jaxb.CreditNoteType;

/**
 * Utility to write {@link CreditNoteType} instances to XML.
 */
public class UblCreditNoteWriter {

    /**
     * Marshal the given credit note to a formatted XML string.
     *
     * @param creditNote the credit note object
     * @return XML representation
     */
    public String writeToString(CreditNoteType creditNote) {
        return UblDocumentWriter.writeToString(
                creditNote,
                CreditNoteType.class,
                new QName("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote"));
    }

    /**
     * Marshal the credit note to the given file path.
     *
     * @param creditNote the credit note to marshal
     * @param output  the target file path
     */
    public void write(CreditNoteType creditNote, Path output) {
        UblDocumentWriter.write(
                creditNote,
                CreditNoteType.class,
                new QName("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote"),
                output);
    }
}
