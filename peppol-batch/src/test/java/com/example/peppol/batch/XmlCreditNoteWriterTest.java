package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import network.oxalis.peppol.ubl2.jaxb.CreditNoteType;

@Slf4j
class XmlCreditNoteWriterTest {

    @Test
    void writesCreditNoteToXmlString() throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/sample-creditnote.xml"));
        XmlCreditNoteReader parser = new XmlCreditNoteReader();
        CreditNoteType creditNote = parser.parse(xml);

        XmlCreditNoteWriter writer = new XmlCreditNoteWriter();
        String out = writer.writeToString(creditNote);

        Path outFile = Path.of("target", "generated-creditnote.xml");
        Files.createDirectories(outFile.getParent());
        writer.write(creditNote, outFile);
        log.info("Written credit note to {}", outFile.toAbsolutePath());

        assertNotNull(out);
        assertFalse(out.isEmpty());

        CreditNoteType parsed = parser.parse(out);

        assertEquals(creditNote.getID().getValue(), parsed.getID().getValue());
        assertTrue(out.contains("<CreditNote xmlns=\"urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2\""));
        assertFalse(out.contains("CommonExtensionComponents-2"));
    }
}
