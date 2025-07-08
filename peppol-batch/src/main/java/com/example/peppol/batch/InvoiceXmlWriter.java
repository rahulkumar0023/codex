package com.example.peppol.batch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Writes invoice XML to files.
 */
public class InvoiceXmlWriter implements ItemWriter<InvoiceDocument> {

    private final Path outputDir;

    public InvoiceXmlWriter(Path outputDir) {
        this.outputDir = outputDir;
        try {
            Files.createDirectories(outputDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create output directory", e);
        }
    }

    private static final Pattern XML_DECL = Pattern.compile("^<\\?xml[^>]*\\?>", Pattern.CASE_INSENSITIVE);

    /**
     * Ensure the XML declaration uses {@code standalone="no"}. If no
     * declaration is present one is added.
     */
    private static String normalizeStandalone(String xml) {
        Matcher m = XML_DECL.matcher(xml);
        if (m.find()) {
            String decl = m.group();
            if (!decl.contains("standalone=\"no\"")) {
                if (decl.contains("standalone=\"yes\"")) {
                    decl = decl.replace("standalone=\"yes\"", "standalone=\"no\"");
                } else {
                    decl = decl.substring(0, decl.length() - 2) + " standalone=\"no\"?>";
                }
            }
            return decl + xml.substring(m.end());
        }
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + xml;
    }

    @Override
    public void write(Chunk<? extends InvoiceDocument> items) throws Exception {
        for (InvoiceDocument doc : items) {
            Path input = doc.getSourceFile();
            String fileName = input.getFileName().toString();
            int idx = fileName.lastIndexOf('.');
            String baseName = idx >= 0 ? fileName.substring(0, idx) : fileName;
            Path out = outputDir.resolve(baseName + ".xml");
            String content = normalizeStandalone(doc.getXml());
            Files.writeString(out, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

}
