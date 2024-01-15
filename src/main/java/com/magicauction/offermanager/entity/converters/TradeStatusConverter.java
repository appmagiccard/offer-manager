package com.magicauction.offermanager.entity.converters;

import com.magicauction.offermanager.entity.TradeStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TradeStatusConverter implements AttributeConverter<TradeStatus, String> {

    @Override
    public String convertToDatabaseColumn(TradeStatus tradeStatus) {
        if (tradeStatus == null) {
            return null;
        }
        return tradeStatus.getLabel();
    }

    @Override
    public TradeStatus convertToEntityAttribute(String label) {
        if (label == null) {
            return null;
        }

        return Stream.of(TradeStatus.values())
                .filter(c -> c.getLabel().equals(label))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}