package dev.danilosales.bank.model.converters;

import dev.danilosales.bank.enums.OperationType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OperationTypeConverter implements AttributeConverter<OperationType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OperationType operationType) {
        if(operationType == null) {
            return null;
        }
        return operationType.getCode();
    }

    @Override
    public OperationType convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }
        return OperationType.getByCode(code);
    }
}
