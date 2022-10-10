package dev.danilosales.bank.validations.account;

import dev.danilosales.bank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class AccountExistsValidator implements ConstraintValidator<AccountExists, Long> {

    private final AccountRepository accountRepository;

    @Override
    public boolean isValid(Long accountId, ConstraintValidatorContext constraintValidatorContext) {
        if(isNull(accountId)) {
            return false;
        }
        return accountRepository.existsById(accountId);
    }
}
