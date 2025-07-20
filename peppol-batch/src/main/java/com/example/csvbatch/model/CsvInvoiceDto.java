package com.example.csvbatch.model;

import lombok.Data;

/**
 * Simple DTO representing invoice data from CSV.
 */
@Data
public class CsvInvoiceDto {
    private String invoiceNumber;
    private String issueDate;
    private String dueDate;
    private String supplierName;
    private String customerName;
    private String currencyId;
    private String lineExtensionAmount;
}
