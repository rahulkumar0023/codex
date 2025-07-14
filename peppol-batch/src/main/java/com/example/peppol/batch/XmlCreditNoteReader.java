package com.example.peppol.batch;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import network.oxalis.peppol.ubl2.jaxb.CreditNoteType;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Utility to parse UBL credit note XML into JAXB objects.
 */
public class XmlCreditNoteReader implements ResourceAwareItemReaderItemStream<CreditNoteType> {

    private Resource resource;
    private boolean read;

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

    // ---------------------------------------------------------------------
    // ResourceAwareItemReaderItemStream implementation
    // ---------------------------------------------------------------------

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
        this.read = false;
    }

    @Override
    public CreditNoteType read() throws Exception {
        if (resource == null || read) {
            return null;
        }
        try (InputStream in = resource.getInputStream()) {
            read = true;
            return parse(in);
        }
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        // nothing to open
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // no state to update
    }

    @Override
    public void close() throws ItemStreamException {
        // nothing to close
    }
}
