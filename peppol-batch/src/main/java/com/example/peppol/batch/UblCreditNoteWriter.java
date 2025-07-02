package com.example.peppol.batch;

import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
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
        try {
            JAXBContext ctx = JAXBContext.newInstance(CreditNoteType.class);
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper", new UblNamespacePrefixMapper());

            var ext = creditNote.getUBLExtensions();
            if (ext != null && ext.getUBLExtension().isEmpty()) {
                creditNote.setUBLExtensions(null);
            }

            StringWriter sw = new StringWriter();
            JAXBElement<CreditNoteType> root = new JAXBElement<>(
                    new QName("urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2", "CreditNote"),
                    CreditNoteType.class, creditNote);

            marshaller.marshal(root, sw);

            String xml = sw.toString();
            if (ext == null || ext.getUBLExtension().isEmpty()) {
                xml = xml.replaceAll(" xmlns:[^=]*=\"urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2\"", "");
            }
            return xml;
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to marshal credit note", e);
        }
    }

    /**
     * Marshal the credit note to the given file path.
     *
     * @param creditNote the credit note to marshal
     * @param output  the target file path
     */
    public void write(CreditNoteType creditNote, Path output) {
        try {
            Files.writeString(output, writeToString(creditNote));
        } catch (Exception e) {
            throw new RuntimeException("Failed to write credit note to " + output, e);
        }
    }
}
