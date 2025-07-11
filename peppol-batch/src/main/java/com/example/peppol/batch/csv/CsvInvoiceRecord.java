package com.example.peppol.batch.csv;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Simple DTO representing a CSV invoice row.
 * <p>
 * Using Lombok keeps the class concise while still exposing the
 * standard getters, setters and builder API.
 * </p>
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsvInvoiceRecord {

    /**
     * Parsed CSV fields keyed by column header.
     * The standard invoice header columns such as CustomizationID,
     * ProfileID and ID can be accessed via the convenience getters.
     */
    private Map<String, String> fields;

    /** Convenience accessor for CustomizationID */
    public String getCustomizationId() {
        return getField("CustomizationID");
    }

    /** Convenience accessor for ProfileID */
    public String getProfileId() {
        return getField("ProfileID");
    }

    /** Convenience accessor for invoice ID */
    public String getId() {
        return getField("ID");
    }

    public String getIssueDate() {
        return getField("IssueDate");
    }

    public String getDueDate() {
        return getField("DueDate");
    }

    public String getInvoiceTypeCode() {
        return getField("InvoiceTypeCode");
    }

    public String getNote() {
        return getField("Note");
    }

    public String getTaxPointDate() {
        return getField("TaxPointDate");
    }

    public String getDocumentCurrencyCode() {
        return getField("DocumentCurrencyCode");
    }

    public String getTaxCurrencyCode() {
        return getField("TaxCurrencyCode");
    }

    /** Convenience accessor for UBLVersionID */
    public String getUblVersionId() {
        return getField("UBLVersionID");
    }

    /** Convenience accessor for ProfileExecutionID */
    public String getProfileExecutionId() {
        return getField("ProfileExecutionID");
    }

    /** Convenience accessor for CopyIndicator */
    public String getCopyIndicator() {
        return getField("CopyIndicator");
    }

    /** Convenience accessor for UUID */
    public String getUuid() {
        return getField("UUID");
    }

    /** Convenience accessor for IssueTime */
    public String getIssueTime() {
        return getField("IssueTime");
    }

    /** Accessor for the chassis value example. */
    public String getChassisValue() {
        return getField("AdditionalItemProperty_cbc_chassisValue");
    }

    /** Generic accessor by header name. */
    public String getField(String name) {
        String value = fields != null ? fields.get(name) : null;
        log.debug("Accessing field '{}': {}", name, value);
        return value;
    }
}
