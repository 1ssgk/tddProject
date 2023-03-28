package com.wonseok.subject.domain.common.converter;

import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.DecimalFormat;

@Converter
public class DecimalConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (!StringUtils.hasText(attribute)) {
            return "0";
        }
        return attribute;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (!StringUtils.hasText(dbData)) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("###,###.###");
        return df.format(Double.valueOf(dbData));
    }
}
