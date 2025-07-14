package com.example.peppol.batch.csv;

public class MyNamespacePrefixMapper extends org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper {
    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        return switch (namespaceUri) {
            case "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2" -> "cac";
            case "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2" -> "cbc";
            case "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2" -> "";
            default -> "";
        };
    }
}
