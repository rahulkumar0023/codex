package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.junit.jupiter.api.Test;

class PdfInvoiceXmlReaderTest {
    @Test
    void readsEmbeddedXml() throws Exception {
        Path dir = Files.createTempDirectory("pdf");
        Path pdf = dir.resolve("invoice.pdf");

        byte[] xmlBytes = "<Invoice>test</Invoice>".getBytes(StandardCharsets.UTF_8);

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDAnnotationFileAttachment att = new PDAnnotationFileAttachment();
            PDComplexFileSpecification fs = new PDComplexFileSpecification();
            fs.setFile("invoice.xml");
            PDEmbeddedFile ef = new PDEmbeddedFile(doc, new ByteArrayInputStream(xmlBytes));
            fs.setEmbeddedFile(ef);
            att.setFile(fs);
            page.getAnnotations().add(att);

            doc.save(pdf.toFile());
        }

        PdfInvoiceXmlReader reader = new PdfInvoiceXmlReader(dir);
        InvoiceDocument doc = reader.read();
        assertNotNull(doc);
        assertEquals("<Invoice>test</Invoice>", doc.getXml());
        assertEquals(pdf, doc.getSourceFile());
        assertNull(reader.read());
    }
}
