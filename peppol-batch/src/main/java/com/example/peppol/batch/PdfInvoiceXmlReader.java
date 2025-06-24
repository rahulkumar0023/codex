package com.example.peppol.batch;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.springframework.batch.item.ItemReader;

/**
 * Reads PDF files from a directory and extracts embedded invoice XML.
 */
public class PdfInvoiceXmlReader implements ItemReader<InvoiceDocument> {

    private final Iterator<Path> pdfIterator;

    public PdfInvoiceXmlReader(Path inputDir) {
        try {
            List<Path> files = Files.list(inputDir)
                .filter(p -> p.toString().toLowerCase().endsWith(".pdf"))
                .collect(Collectors.toList());
            this.pdfIterator = files.iterator();
        } catch (IOException e) {
            throw new RuntimeException("Failed to list PDF files", e);
        }
    }

    @Override
    public InvoiceDocument read() throws Exception {
        if (!pdfIterator.hasNext()) {
            return null; // no more files
        }
        Path pdfPath = pdfIterator.next();
        String xml = extractInvoiceXml(pdfPath);
        if (xml == null) {
            // no embedded xml found, skip
            return read();
        }
        return new InvoiceDocument(xml, pdfPath);
    }

    private String extractInvoiceXml(Path pdf) {
        try (PDDocument document = PDDocument.load(pdf.toFile())) {
            PDPage firstPage = document.getPage(0);

            for (PDAnnotation annotation : firstPage.getAnnotations()) {
                if (annotation instanceof PDAnnotationFileAttachment attachment) {

                    // Cast to PDComplexFileSpecification
                    if (attachment.getFile() instanceof PDComplexFileSpecification fileSpec) {
                        String filename = fileSpec.getFile();  // Correct method to get the filename

                        if (filename != null && filename.toLowerCase().endsWith(".xml")) {
                            PDEmbeddedFile embeddedFile = fileSpec.getEmbeddedFile();

                            if (embeddedFile != null) {
                                return new String(embeddedFile.toByteArray(), StandardCharsets.UTF_8);
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read PDF " + pdf, e);
        }

        return null;
    }

}
