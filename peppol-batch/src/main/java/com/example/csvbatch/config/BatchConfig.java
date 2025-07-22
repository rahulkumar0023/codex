package com.example.csvbatch.config;

import com.example.csvbatch.model.CsvInvoiceDto;
import com.example.csvbatch.processor.InvoiceProcessor;
import com.example.csvbatch.writer.XmlInvoiceWriter;
import lombok.RequiredArgsConstructor;
import network.oxalis.peppol.ubl2.jaxb.InvoiceType;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.nio.file.Path;

import static com.example.csvbatch.CsvFieldNames.INVOICE_FIELDS;

@Configuration
@EnableBatchProcessing
public class BatchConfig {


    private final InvoiceProcessor processor;
    public BatchConfig(InvoiceProcessor processor) {
        this.processor = processor;
    }

    @Bean
    public FlatFileItemReader<CsvInvoiceDto> reader() {
        return new FlatFileItemReaderBuilder<CsvInvoiceDto>()
                .name("csvInvoiceReader")
                .resource(new FileSystemResource("input/invoices.csv"))
                .linesToSkip(1)
                .delimited().delimiter(";")
                .names(INVOICE_FIELDS)
                //.names("invoiceNumber", "issueDate", "dueDate", "invoiceTypeCode", "note", "buyerReference", "startDate", "endDate", "contractDocumentReferenceCbcId", "supplierEndPoint", "supplierPartyIdentificationCbcId", "supplierPartyNameCbcName", "supplierStreetName", "supplierAdditionalStreetName", "supplierCityName", "supplierPostalZone", "supplierCountrySubentity", "supplierAddressLineCbcLine", "supplierCountryCbcIdentificationCode", "customerEndPoint", "customerPartyIdentificationCbcId", "customerPartyNameCbcName", "customerStreetName", "customerAdditionalStreetName", "customerCityName", "customerPostalZone", "customerCountrySubentity", "customerAddressLineCbcLine", "customerCountryCbcIdentificationCode", "customerRegistrationName", "paymentMeans", "paymentMeansCbcPaymentId", "legalMonetaryTotalCbcLineExtensionAmount", "legalMonetaryTotalCbcTaxExclusiveAmount", "legalMonetaryTotalCbcTaxInclusiveAmount", "legalMonetaryTotalCbcPayableAmount", "invoiceLineCbcId", "invoiceLineCbcInvoicedQuantity", "invoiceLineCbcLineExtensionAmount", "currencyId", "itemCbcName", "invoicePeriodCbcStartDate", "invoicePeriodCbcEndDate", "descriptionCbcItem", "allowanceChargeCbcChargeIndicator", "allowanceChargeCbcAmount", "allowanceChargeCbcBaseAmount", "baseAmountCbcCurrencyId", "allowanceChargeReason")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(CsvInvoiceDto.class);
                }})
                .build();
    }

    @Bean
    public XmlInvoiceWriter xmlInvoiceWriter() {
        return new XmlInvoiceWriter(Path.of("output")); // or Path.of("target/xml-out")
    }

    @Bean
    public Step csvToXmlStep(JobRepository repo, PlatformTransactionManager tx, XmlInvoiceWriter writer) {
        return new StepBuilder("csvToXmlStep", repo)
                .<CsvInvoiceDto, InvoiceType>chunk(10, tx)
                .reader(reader())
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job invoiceJob(JobRepository repo, Step step) {
        return new JobBuilder("invoiceJob", repo)
                .start(step)
                .build();
    }
}
