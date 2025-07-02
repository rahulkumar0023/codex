package com.example.peppol.batch;

import java.io.StringReader;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

/**
 * Utility to parse UBL 2.1 invoice XML into JAXB objects.
 */
public class UblInvoiceParser {

    /**
     * Parse the given XML string into an {@link InvoiceType} instance.
     *
     * @param xml invoice XML
     * @return parsed {@link InvoiceType}
     */
    public InvoiceType parse(String xml) {
        try {
            JAXBContext ctx = JAXBContext.newInstance("network.oxalis.peppol.ubl2.jaxb");
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            JAXBElement<InvoiceType> root = (JAXBElement<InvoiceType>) unmarshaller.unmarshal(new StringReader(xml));
            InvoiceType invoice = root.getValue();
            return invoice;
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to parse invoice", e);
        }
    }
}
