package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import network.oxalis.peppol.ubl2.jaxb.CreditNoteType;

class UblCreditNoteParserTest {

    @Test
    void parsesSampleCreditNote() throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/sample-creditnote.xml"));
        UblCreditNoteParser parser = new UblCreditNoteParser();
        CreditNoteType creditNote = parser.parse(xml);
        assertNotNull(creditNote);
        assertEquals("CN758494", creditNote.getID().getValue());
    }

    @Test
    void parsesFromInputStream() throws Exception {
        try (var in = Files.newInputStream(Path.of("src/test/resources/sample-creditnote.xml"))) {
            UblCreditNoteParser parser = new UblCreditNoteParser();
            CreditNoteType creditNote = parser.parse(in);
            assertNotNull(creditNote);
            assertEquals("CN758494", creditNote.getID().getValue());
        }
    }
}
