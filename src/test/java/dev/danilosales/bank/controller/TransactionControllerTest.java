package dev.danilosales.bank.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;

import dev.danilosales.bank.BaseTest;
import dev.danilosales.bank.dto.AccountDTO;
import dev.danilosales.bank.dto.TransactionDTO;
import dev.danilosales.bank.model.Account;
import dev.danilosales.bank.repository.AccountRepository;
import dev.danilosales.bank.service.AccountService;
import lombok.SneakyThrows;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.money.Monetary;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc()
@Transactional
class TransactionControllerTest extends BaseTest {

    static String TRANSACTION_ENDPOINT = "/transactions";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    @DisplayName("Should create a valid Transaction")
    @SneakyThrows
    @Rollback
    public void shouldCreateTransaction() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .value(Money.of(100, Monetary.getCurrency("BRL")))
                .operationType(4)
                .accountId(account.getId())
                .build();

        mockMvc.perform(post(TRANSACTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.value.amount").value(transactionDTO.getValue().getNumber().intValue()))
                .andExpect(jsonPath("$.value.currency").value(transactionDTO.getValue().getCurrency().getCurrencyCode()))
                .andExpect(jsonPath("$.operation_type").value(transactionDTO.getOperationType()))
                .andExpect(jsonPath("$.account_id").value(transactionDTO.getAccountId()))
                .andReturn();
    }

    @Test
    @DisplayName("Should fail to persist transaction with nonexistent Operation Type")
    @SneakyThrows
    public void shouldFailToCreateTransactionWithNonexistentOperationType() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .value(Money.of(100, Monetary.getCurrency("BRL")))
                .operationType(9999)
                .accountId(account.getId())
                .build();

        MvcResult result = mockMvc.perform(post(TRANSACTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                containsString("Operation Type does not exists"));

    }

    @Test
    @DisplayName("Should fail to persist transaction with nonexistent Account")
    @SneakyThrows
    public void shouldFailToCreateTransactionWithNonexistentAccount() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .value(Money.of(100, Monetary.getCurrency("BRL")))
                .operationType(1)
                .accountId(10000L)
                .build();

        MvcResult result = mockMvc.perform(post(TRANSACTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage(),
                containsString("Account does not exists"));

    }

    @Test
    @DisplayName("Should fail to persist transaction with negative amount value")
    @SneakyThrows
    public void shouldFailToCreateTransactionWithNegativeAmountValue() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .value(Money.of(-100, Monetary.getCurrency("BRL")))
                .operationType(1)
                .accountId(account.getId())
                .build();

        mockMvc.perform(post(TRANSACTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transactionDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @DisplayName("Should fail to persist transaction with all fields incorrect")
    @Rollback
    public void shouldFailToCreateInvalidTransaction() throws Exception {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .value(Money.of(100, Monetary.getCurrency("BRL")))
                .operationType(9999)
                .accountId(10000L)
                .build();

        MvcResult result = mockMvc.perform(post(TRANSACTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String exceptionMessage = Objects.requireNonNull(result.getResolvedException()).getMessage();
        assertThat(exceptionMessage, containsString("Account does not exists"));
        assertThat(exceptionMessage, containsString("Operation Type does not exists"));
    }

}
