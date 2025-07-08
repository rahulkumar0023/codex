package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.namespace.QName;

import org.junit.jupiter.api.Test;

class UblDocumentWriterTest {

    public static class DummyDoc {
        private UBLExtensions extensions;
        public static class UBLExtensions {
            private java.util.List<Object> ublExtension = new java.util.ArrayList<>();
            public java.util.List<Object> getUBLExtension() { return ublExtension; }
        }
        public UBLExtensions getUBLExtensions() { return extensions; }
        public void setUBLExtensions(UBLExtensions extensions) { this.extensions = extensions; }
    }

    @Test
    void writesXmlWithoutExtensionsNamespace() throws Exception {
        DummyDoc doc = new DummyDoc();
        doc.setUBLExtensions(new DummyDoc.UBLExtensions());
        String xml = UblDocumentWriter.writeToString(doc, DummyDoc.class, new QName("urn:test", "Dummy"));
        assertFalse(xml.contains("CommonExtensionComponents-2"));

        Path out = Files.createTempFile("dummy", ".xml");
        UblDocumentWriter.write(doc, DummyDoc.class, new QName("urn:test", "Dummy"), out);
        assertTrue(Files.exists(out));
    }

    @Test
    void addsXmlDeclarationWithStandaloneNo() throws Exception {
        DummyDoc doc = new DummyDoc();
        String xml = UblDocumentWriter.writeToString(doc, DummyDoc.class, new QName("urn:test", "Dummy"));
        assertTrue(xml.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"));
    }
}
