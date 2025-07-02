package com.example.peppol.batch;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * Provides stable namespace prefixes for UBL invoice marshalling.
 */
public class UblNamespacePrefixMapper extends NamespacePrefixMapper {
    private static final String INVOICE_NS = "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2";
    private static final String CAC_NS = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2";
    private static final String CBC_NS = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        return switch (namespaceUri) {
            case INVOICE_NS -> ""; // default namespace
            case CAC_NS -> "cac";
            case CBC_NS -> "cbc";
            default -> suggestion;
        };
    }
}
