package com.example.csvbatch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SmartCsvInvoiceFieldSetMapper implements FieldSetMapper<CsvInvoiceDto> {

    private final Map<String, String> normalizedFieldToRealName = new HashMap<>();

    public SmartCsvInvoiceFieldSetMapper() {
        for (PropertyDescriptor pd : org.springframework.beans.BeanUtils.getPropertyDescriptors(CsvInvoiceDto.class)) {
            String name = pd.getName();
            normalizedFieldToRealName.put(normalize(name), name);
        }
    }

    @Override
    public CsvInvoiceDto mapFieldSet(FieldSet fieldSet) throws BindException {
        CsvInvoiceDto dto = new CsvInvoiceDto();

        for (String rawName : fieldSet.getNames()) {
            String normalized = normalize(rawName);
            String realField = normalizedFieldToRealName.get(normalized);

            if (realField != null) {
                try {
                    PropertyDescriptor pd = new PropertyDescriptor(realField, CsvInvoiceDto.class);
                    Method writeMethod = pd.getWriteMethod();
                    if (writeMethod != null) {
                        writeMethod.invoke(dto, fieldSet.readString(rawName));
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Error setting field: " + realField, e);
                }
            }
        }
        return dto;
    }

    private String normalize(String name) {
        return name.trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }
}