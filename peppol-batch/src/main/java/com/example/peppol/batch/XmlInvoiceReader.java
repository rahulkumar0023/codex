package com.example.peppol.batch;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.core.io.Resource;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

/**
 * Utility to parse UBL 2.1 invoice XML into JAXB objects.
 *
 * <p>This class acts as the reader counterpart to {@link XmlInvoiceWriter} and
 * exposes a simple {@link #parse(String)} method for converting XML into
 * {@link InvoiceType} objects.</p>
 */
public class XmlInvoiceReader implements ResourceAwareItemReaderItemStream<InvoiceType> {

    private Resource resource;
    private boolean read;

    /**
     * Parse the given XML string into an {@link InvoiceType} instance.
     *
     * @param xml invoice XML
     * @return parsed {@link InvoiceType}
     */
    public InvoiceType parse(String xml) {
        return parse(new java.io.ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Parse invoice XML from the given input stream.
     *
     * @param xmlStream input stream containing invoice XML
     * @return parsed {@link InvoiceType}
     */
    public InvoiceType parse(InputStream xmlStream) {
        try {
            JAXBContext ctx = JAXBContext.newInstance("network.oxalis.peppol.ubl2.jaxb");
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            JAXBElement<InvoiceType> root = (JAXBElement<InvoiceType>) unmarshaller.unmarshal(xmlStream);
            return root.getValue();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to parse invoice", e);
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
    public InvoiceType read() throws Exception {
        if (resource == null || read) {
            return null;
        }
        try (InputStream in = resource.getInputStream()) {
            StepContext ctx = StepSynchronizationManager.getContext();
            if (ctx != null) {
                ctx.getStepExecution().getExecutionContext()
                        .put("current.file.path", resource.getFile().getAbsolutePath());
            }
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
