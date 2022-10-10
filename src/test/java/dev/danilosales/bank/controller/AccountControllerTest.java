package dev.danilosales.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import dev.danilosales.bank.BaseTest;
import dev.danilosales.bank.dto.AccountDTO;
import lombok.SneakyThrows;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class AccountControllerTest extends BaseTest {

    private static String ACCOUNT_ENDPOINT = "/accounts";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should persist account and retrieve account")
    @SneakyThrows
    @Rollback
    public void shouldPersistAccount() {
        var accountInputDTO = AccountDTO.builder().documentNumber("1234").name("Danilo Sales").build();

        var createAccountResult = mockMvc.perform(post(ACCOUNT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountInputDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        var accountId = JsonPath.read(createAccountResult.getResponse().getContentAsString(),
                "$.account_id");

        mockMvc.perform(get(String.format("%s/%s", ACCOUNT_ENDPOINT, accountId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.document_number").value("1234"))
                .andExpect(jsonPath("$.name").value("Danilo Sales"));
    }

    @Test
    @DisplayName("Should fail to persist account with empty document number")
    @SneakyThrows
    public void shouldFailToPersistAccount() {
        var accountInputDTO = AccountDTO.builder().documentNumber(null).build();

        mockMvc.perform(post(ACCOUNT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountInputDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

}
