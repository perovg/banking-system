package com.pgm.nad.BankingSystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@Transactional
@Rollback(value = false)
class ClientCreateBankAccountControllerTest extends CommonMvcTest {
    private final String adminId = "700000000";
    private final String userId = "700000001";
    private final String clientId = "1"; //existing client id
    private final String bankId = "1"; //existing bank id

    @Test
    @Order(1)
    void addBank() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/add_bank")
                        .param("clientId", clientId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("client"))
                .andExpect(model().attributeExists("banks"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientCreateAccounts/clientAddBank"));
    }

    @Test
    @Order(2)
    void createAccountInNewBank() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/add_bank/create_account")
                        .param("clientId", clientId)
                        .param("bankId", bankId)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bank"))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientCreateAccounts/clientCreateBankAccount"));
    }

    @Test
    @Order(3)
    void conditions() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/accounts/create/conditions")
                        .param("clientId", clientId)
                        .param("bankId", bankId)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bank"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientCreateAccounts/clientCreateBankAccountConditions"));
    }

    @Test
    @Order(4)
    void successOfCreationBankAccountInNewBank() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/accounts/create/conditions")
                        .param("clientId", clientId)
                        .param("bankId", bankId)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bank"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientCreateAccounts/clientCreateBankAccountSuccess"));
    }
}