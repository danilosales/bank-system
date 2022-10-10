package dev.danilosales.bank.validations.operationtype;

import dev.danilosales.bank.enums.OperationType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OperationTypeExistsValidator implements ConstraintValidator<OperationTypeExists, Integer> {
    private List<Integer> acceptedValues;


    @Override
    public void initialize(OperationTypeExists operationType) {
        acceptedValues = Stream.of(OperationType.values())
                .map(OperationType::getCode)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }

        return acceptedValues.contains(value);
    }
}
