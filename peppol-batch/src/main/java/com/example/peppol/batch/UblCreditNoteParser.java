package com.example.peppol.batch;

import java.io.StringReader;

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
        try {
            JAXBContext ctx = JAXBContext.newInstance("network.oxalis.peppol.ubl2.jaxb");
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            JAXBElement<CreditNoteType> root = (JAXBElement<CreditNoteType>) unmarshaller.unmarshal(new StringReader(xml));
            return root.getValue();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to parse credit note", e);
        }
    }
}
