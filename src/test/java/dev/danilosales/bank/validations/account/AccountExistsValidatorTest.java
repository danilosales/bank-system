package dev.danilosales.bank.validations.account;

import dev.danilosales.bank.repository.AccountRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountExistsValidatorTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    ConstraintValidatorContext context;

    @InjectMocks
    AccountExistsValidator accountExistsValidator;

    @Test
    @DisplayName("Should return true when account already exists")
    public void shouldReturnTrueWhenAccountAlreadyExists() {
        var existingAccountId = 1L;
        when(accountRepository.existsById(any())).thenReturn(true);
        assertTrue(accountExistsValidator.isValid(existingAccountId, context));
    }

    @Test
    @DisplayName("Should return false when account doesn't exists")
    public void shouldReturnFalseWhenAccountDoesNotExists() {
        var nonExistingAccountId = 1L;
        when(accountRepository.existsById(any())).thenReturn(false);
        assertFalse(accountExistsValidator.isValid(nonExistingAccountId, context));
    }

    @Test
    @DisplayName("Should return false when account id is null")
    public void shouldReturnFalseWhenAccountIdIsNull() {
        assertFalse(accountExistsValidator.isValid(null, context));
    }

}
