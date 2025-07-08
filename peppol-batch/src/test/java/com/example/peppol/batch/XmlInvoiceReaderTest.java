package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;

import org.junit.jupiter.api.Test;

import network.oxalis.peppol.ubl2.jaxb.InvoiceType;

class XmlInvoiceReaderTest {

    @Test
    void parsesComplexInvoice() throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/complex-invoice.xml"));
        XmlInvoiceReader parser = new XmlInvoiceReader();
        InvoiceType invoice = parser.parse(xml);
        assertNotNull(invoice);
        assertEquals("TickstarAP-BIS3-test-01", invoice.getID().getValue());
    }

    @Test
    void readsInvoiceAsItemReader() throws Exception {
        XmlInvoiceReader reader = new XmlInvoiceReader();
        reader.setResource(new org.springframework.core.io.FileSystemResource("src/test/resources/complex-invoice.xml"));

        StepExecution stepExecution = new StepExecution("test", new JobExecution(1L));
        StepSynchronizationManager.register(stepExecution);

        reader.open(stepExecution.getExecutionContext());
        InvoiceType invoice = reader.read();
        reader.close();
        StepSynchronizationManager.close();

        assertNotNull(invoice);
        assertEquals("TickstarAP-BIS3-test-01", invoice.getID().getValue());
        assertEquals(Path.of("src/test/resources/complex-invoice.xml").toAbsolutePath().toString(),
                stepExecution.getExecutionContext().get("current.file.path"));
        // Ensure subsequent reads return null
        assertNull(reader.read());
    }
}
