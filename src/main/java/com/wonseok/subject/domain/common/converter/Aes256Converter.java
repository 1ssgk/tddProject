package com.wonseok.subject.domain.common.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.wonseok.subject.domain.common.encryption.Aes256;
import org.springframework.util.StringUtils;

@Converter
public class Aes256Converter implements AttributeConverter<String, String> {

    private final Aes256 aes256;

    public Aes256Converter() {
        this.aes256 = new Aes256();
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (!StringUtils.hasText(attribute)) {
            return attribute;
        }
        try {
            return aes256.encrypt(attribute);
        } catch (Exception e) {
            throw new RuntimeException("failed to encrypt data", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return aes256.decrypt(dbData);
        } catch (Exception e) {
            return dbData;
        }
    }
}