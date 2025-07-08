package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class InvoiceDocumentTest {
    @Test
    void gettersReturnValues() {
        Path source = Path.of("invoice1.xml");
        InvoiceDocument doc = new InvoiceDocument("<Invoice/>", source);
        assertEquals("<Invoice/>", doc.getXml());
        assertEquals(source, doc.getSourceFile());
    }
}
