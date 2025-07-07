package com.example.peppol.batch;

import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import network.oxalis.peppol.ubl2.jaxb.CreditNoteType;

/**
 * Utility to parse UBL credit note XML into JAXB objects.
 */
public class UblCreditNoteParser {

    /**
     * Parse the given XML string into a {@link CreditNoteType} instance.
     *
     * @param xml credit note XML
     * @return parsed {@link CreditNoteType}
     */
    public CreditNoteType parse(String xml) {
        return parse(new java.io.ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Parse credit note XML from the given input stream.
     *
     * @param xmlStream input stream containing credit note XML
     * @return parsed {@link CreditNoteType}
     */
    public CreditNoteType parse(InputStream xmlStream) {
        try {
            JAXBContext ctx = JAXBContext.newInstance("network.oxalis.peppol.ubl2.jaxb");
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            JAXBElement<CreditNoteType> root = (JAXBElement<CreditNoteType>) unmarshaller.unmarshal(xmlStream);
            return root.getValue();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to parse credit note", e);
        }
    }
}
