package com.example.peppol.batch;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
            JAXBContext ctx = JAXBContext.newInstance(InvoiceType.class);
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            return (InvoiceType) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to parse invoice", e);
        }
    }
}
