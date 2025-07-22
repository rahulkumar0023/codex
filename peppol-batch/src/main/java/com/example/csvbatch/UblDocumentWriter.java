package com.example.csvbatch;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import javax.xml.namespace.QName;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class UblDocumentWriter {

    private UblDocumentWriter() {
    }

    public static <T> String writeToString(T document, Class<T> type, QName qName) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(type);
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper", new UblNamespacePrefixMapper());
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            Object extensions = type.getMethod("getUBLExtensions").invoke(document);
            boolean emptyExtensions = false;
            if (extensions != null) {
                @SuppressWarnings("unchecked")
                List<Object> list = (List<Object>) extensions.getClass().getMethod("getUBLExtension").invoke(extensions);
                if (list.isEmpty()) {
                    type.getMethod("setUBLExtensions", extensions.getClass()).invoke(document, new Object[]{null});
                    emptyExtensions = true;
                }
            }

            StringWriter sw = new StringWriter();
            sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
            JAXBElement<T> root = new JAXBElement<>(qName, type, document);
            marshaller.marshal(root, sw);

            String xml = sw.toString();
            if (extensions == null || emptyExtensions) {
                xml = xml.replaceAll(" xmlns:[^=]*=\"urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2\"", "");
            }
            return xml;
        } catch (JAXBException | ReflectiveOperationException e) {
            throw new RuntimeException("Failed to marshal UBL document", e);
        }
    }

    public static <T> void write(T document, Class<T> type, QName qName, Path output) {
        try {
            Files.writeString(output, writeToString(document, type, qName));
        } catch (Exception e) {
            throw new RuntimeException("Failed to write UBL document to " + output, e);
        }
    }
}

