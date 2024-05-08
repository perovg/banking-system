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
class ClientMainControllerTest extends CommonMvcTest {
    private final String adminId = "700000000";
    private final String userId = "700000001";
    private final String clientId = "1"; //existing client id
    private final String bankId = "1"; //existing bank id

    @Test
    @Order(1)
    void mainCatalogUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/banks")
                        .param("clientId", clientId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("client/clientBanks"));
    }

    @Test
    @Order(5)
    void mainCatalogAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/banks")
                        .param("clientId", adminId))
                .andExpect(status().isRequestTimeout())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/admin"));
    }

    @Test
    @Order(2)
    void bankCatalog() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/banks/accounts")
                        .param("bankId", bankId)
                        .param("clientId", clientId)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("accounts"))
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bank"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("client/clientBankBankAccounts"));
    }

    @Test
    void checkSignInAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks")
                        .param("clientId", adminId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"))
                .andExpect(model().errorCount(0));
    }

    @Test
    void checkSignInUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks")
                        .param("clientId", userId)) // нужно вставить айди пользователя существующего
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("client/clientBanks"))
                .andExpect(model().errorCount(0));
    }

    @Test
    void checkSignInNotExistingUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks")
                        .param("clientId", clientId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/signIn"))
                .andExpect(model().errorCount(0));
    }
}