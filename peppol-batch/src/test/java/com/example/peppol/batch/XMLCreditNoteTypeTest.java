package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import network.oxalis.peppol.ubl2.jaxb.CreditNoteType;
import com.example.peppol.batch.XMLCreditNoteType;

class XMLCreditNoteTypeTest {

    @Test
    void parsesSampleCreditNote() throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/sample-creditnote.xml"));
        XMLCreditNoteType parser = new XMLCreditNoteType();
        CreditNoteType creditNote = parser.parse(xml);
        assertNotNull(creditNote);
        assertEquals("CN758494", creditNote.getID().getValue());
    }
}
