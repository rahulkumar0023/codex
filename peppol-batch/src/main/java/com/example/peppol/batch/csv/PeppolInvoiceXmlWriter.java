package com.example.peppol.batch.csv;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.io.File;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PeppolInvoiceXmlWriter implements ItemWriter<InvoiceType> {

    private final JAXBContext jaxbContext = createContext();

    private static JAXBContext createContext() {
        try {
            return JAXBContext.newInstance("network.oxalis.peppol.ubl2.jaxb");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(List<? extends InvoiceType> items) throws Exception {
        for (InvoiceType invoice : items) {
            String invoiceId = invoice.getID() != null ? invoice.getID().getValue() : "unknown";
            QName rootName = new QName("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "Invoice");
            JAXBElement<InvoiceType> root = new JAXBElement<>(rootName, InvoiceType.class, invoice);

            File outputFile = new File("/tmp/processed/invoices/" + invoiceId + ".xml");

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNamespacePrefixMapper());

            marshaller.marshal(root, outputFile);

            log.info("Wrote invoice to {}", outputFile.getAbsolutePath());
        }
    }
}
