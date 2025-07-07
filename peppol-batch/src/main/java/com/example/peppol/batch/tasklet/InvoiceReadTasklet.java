package com.example.peppol.batch.tasklet;

import com.example.peppol.batch.CreditNoteRecord;
import com.example.peppol.batch.InvoiceRecord;
import com.example.peppol.batch.XmlCreditNoteReader;
import com.example.peppol.batch.XmlInvoiceReader;
import network.oxalis.peppol.ubl2.jaxb.CreditNoteType;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Tasklet that reads invoice XML files and stores them in the job context.
 */
public class InvoiceReadTasklet implements Tasklet {

    private final Path inputDir;

    public InvoiceReadTasklet(Path inputDir) {
        this.inputDir = inputDir;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        XmlInvoiceReader invoiceParser = new XmlInvoiceReader();
        XmlCreditNoteReader creditNoteParser = new XmlCreditNoteReader();

        List<InvoiceRecord> invoices = new ArrayList<>();
        List<CreditNoteRecord> creditNotes = new ArrayList<>();

        List<Path> files = Files.list(inputDir)
                .filter(p -> p.toString().toLowerCase().endsWith(".xml"))
                .collect(Collectors.toList());

        XMLInputFactory factory = XMLInputFactory.newFactory();
        for (Path file : files) {
            String root = null;
            try (InputStream in = Files.newInputStream(file)) {
                XMLStreamReader xr = factory.createXMLStreamReader(in);
                while (xr.hasNext()) {
                    if (xr.next() == XMLStreamConstants.START_ELEMENT) {
                        root = xr.getLocalName();
                        break;
                    }
                }
                xr.close();
            }

            if (root == null) {
                continue;
            }

            try (InputStream in = Files.newInputStream(file)) {
                if ("Invoice".equals(root)) {
                    InvoiceType invoice = invoiceParser.parse(in);
                    invoices.add(new InvoiceRecord(invoice, file));
                } else if ("CreditNote".equals(root)) {
                    CreditNoteType cn = creditNoteParser.parse(in);
                    creditNotes.add(new CreditNoteRecord(cn, file));
                }
            }
        }

        var ctx = chunkContext.getStepContext().getStepExecution()
                .getJobExecution().getExecutionContext();
        ctx.put("invoices", invoices);
        ctx.put("creditNotes", creditNotes);
        return RepeatStatus.FINISHED;
    }
}
