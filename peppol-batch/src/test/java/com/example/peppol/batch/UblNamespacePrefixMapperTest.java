package com.example.peppol.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UblNamespacePrefixMapperTest {
    @Test
    void returnsPreferredPrefixes() {
        UblNamespacePrefixMapper mapper = new UblNamespacePrefixMapper();
        assertEquals("", mapper.getPreferredPrefix("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", "x", false));
        assertEquals("cac", mapper.getPreferredPrefix("urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", "x", false));
        assertEquals("cbc", mapper.getPreferredPrefix("urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", "x", false));
        assertEquals("suggest", mapper.getPreferredPrefix("urn:other", "suggest", false));
    }
}
