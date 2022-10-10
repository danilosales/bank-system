package dev.danilosales.bank.service;

import dev.danilosales.bank.BaseTest;
import dev.danilosales.bank.dto.AccountDTO;
import dev.danilosales.bank.dto.TransactionDTO;
import dev.danilosales.bank.model.Account;
import dev.danilosales.bank.repository.AccountRepository;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.money.Monetary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@Transactional
class TransactionServiceTest extends BaseTest {

    @Autowired
    TransactionService transactionService;

    static Account account;

    @BeforeAll
    public static void setupAccount(@Autowired AccountService accountService) {
        account = accountService.save(AccountDTO.builder().documentNumber("1234").name("Danilo Sales").build());
    }

    @AfterAll
    public static void cleanUp(@Autowired AccountRepository accountRepository) {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("Should successfully persist and retrieve transaction")
    @Rollback
    void shouldPersistAndRetrieveValidTransaction() {
        var transactionDTO = new TransactionDTO();
        transactionDTO.setAccountId(account.getId());
        transactionDTO.setOperationType(1);
        transactionDTO.setValue(Money.of(100, Monetary.getCurrency("BRL")));

        var transaction = transactionService.save(transactionDTO);

        assertThat(transactionDTO.getAccountId(), equalTo(transaction.getAccount().getId()));
        assertThat(transactionDTO.getOperationType(),
                equalTo(transaction.getOperationType().getCode()));
        assertThat(transactionDTO.getValue(), equalTo(transaction.getValue().negate()));
    }

}
