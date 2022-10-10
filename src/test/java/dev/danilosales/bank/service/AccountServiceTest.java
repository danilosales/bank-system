package dev.danilosales.bank.service;

import dev.danilosales.bank.BaseTest;
import dev.danilosales.bank.dto.AccountDTO;
import dev.danilosales.bank.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class AccountServiceTest extends BaseTest {

    @Autowired
    AccountService accountService;

    @Test
    @DisplayName("Should successfully persist and retrieve account")
    @Rollback
    void shouldPersistAndRetrieveValidAccount() {
        var accountDTO = new AccountDTO();
        accountDTO.setDocumentNumber("1234");
        accountDTO.setName("Danilo Sales");

        var account = accountService.save(accountDTO);

        var accountOnBD = accountService.findById(account.getId());
        assertThat(accountDTO.getDocumentNumber(), equalTo(accountOnBD.getDocumentNumber()));
        assertThat(accountDTO.getName(), equalTo(accountOnBD.getName()));
    }

    @Test
    @DisplayName("Should throw exception when search for Account with nonexistent ID")
    void shouldThrowExceptionForNonexistentID() {
        EntityNotFoundException thrownException = assertThrows(EntityNotFoundException.class,
                () -> accountService.findById(1L));
        assertThat(thrownException.getMessage(), containsString("Account was not found for parameters {id=1}"));
    }

}
