package com.example.peppol.batch.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.core.io.FileSystemResource;

class CsvInvoiceReaderTest {

    @Test
    void readsSingleInvoice() throws Exception {
        CsvInvoiceReader reader = new CsvInvoiceReader();
        reader.setResource(new FileSystemResource(Path.of("src/test/resources/sample-invoice.csv")));

        StepExecution stepExecution = new StepExecution("test", new JobExecution(1L));
        StepSynchronizationManager.register(stepExecution);

        reader.open(stepExecution.getExecutionContext());
        CsvInvoiceRecord rec = reader.read();
        reader.close();
        StepSynchronizationManager.close();

        assertNotNull(rec);
        assertEquals("TickstarAP-BIS3-test-01", rec.getId());
        assertEquals("urn:cen.eu:en16931:2017#compliant#urn:fdc:peppol.eu:2017:poacc:billing:3.0", rec.getCustomizationID());
        assertEquals("EUR", rec.getDocumentCurrencyCode());
    }
}
