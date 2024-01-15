package com.magicauction.offermanager.entity.converters;

import com.magicauction.offermanager.entity.CardState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class CardStateConverter implements AttributeConverter<CardState, String> {

    @Override
    public String convertToDatabaseColumn(CardState tradeStatus) {
        if (tradeStatus == null) {
            return null;
        }
        return tradeStatus.getLabel();
    }

    @Override
    public CardState convertToEntityAttribute(String label) {
        if (label == null) {
            return null;
        }

        return Stream.of(CardState.values())
                .filter(c -> c.getLabel().equals(label))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}