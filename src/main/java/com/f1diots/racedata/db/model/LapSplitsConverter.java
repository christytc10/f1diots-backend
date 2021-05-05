package com.f1diots.racedata.db.model;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class LapSplitsConverter implements
        AttributeConverter<List<Long>, String> {

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<Long> splits) {
        if (splits == null) {
            return null;
        }

        return StringUtils.join(splits, SEPARATOR);
    }

    @Override
    public List<Long> convertToEntityAttribute(String splitsString) {
        if (splitsString == null || splitsString.isEmpty()) {
            return null;
        }

        String[] pieces = splitsString.split(SEPARATOR);

        if (pieces.length == 0) {
            return null;
        }

        return Arrays.stream(pieces).map(Long::valueOf).collect(Collectors.toList());
    }
}