package com.example.peppol.batch;

import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import network.oxalis.peppol.ubl2.jaxb.ObjectFactory;

/**
 * Utility to write {@link InvoiceType} instances to XML.
 */
public class UblInvoiceWriter {

    /**
     * Marshal the given invoice to a formatted XML string.
     *
     * @param invoice the invoice object
     * @return XML representation
     */
    public String writeToString(InvoiceType invoice) {
        try {
            JAXBContext ctx = JAXBContext.newInstance("network.oxalis.peppol.ubl2.jaxb");
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            JAXBElement<InvoiceType> root = new ObjectFactory().createInvoice(invoice);
            marshaller.marshal(root, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to marshal invoice", e);
        }
    }

    /**
     * Marshal the invoice to the given file path.
     *
     * @param invoice the invoice to marshal
     * @param output  the target file path
     */
    public void write(InvoiceType invoice, Path output) {
        try {
            Files.writeString(output, writeToString(invoice));
        } catch (Exception e) {
            throw new RuntimeException("Failed to write invoice to " + output, e);
        }
    }
}
