package dev.danilosales.bank.validations.operationtype;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class OperationTypeExistsValidatorTest {

    @Mock
    ConstraintValidatorContext context;

    @InjectMocks
    OperationTypeExistsValidator operationTypeExistsValidator;

    @Before
    public void setUp() {
        operationTypeExistsValidator.initialize(null);
    }

    @Test
    @DisplayName("Should return true when operation type is valid")
    public void shouldReturnTrueWhenOperationTypeExists() {
        assertTrue(operationTypeExistsValidator.isValid(1, context));
        assertTrue(operationTypeExistsValidator.isValid(2, context));
        assertTrue(operationTypeExistsValidator.isValid(3, context));
        assertTrue(operationTypeExistsValidator.isValid(4, context));
    }

    @Test
    @DisplayName("Should return false when operation type doesn't exists")
    public void shouldReturnFalseWhenOperationTypeDoesNotExists() {
        assertFalse(operationTypeExistsValidator.isValid(0, context));
    }

    @Test
    @DisplayName("Should return false when operation type id is null")
    public void shouldReturnFalseWhenOperationTypeIdIsNull() {
        assertFalse(operationTypeExistsValidator.isValid(null, context));
    }
}
