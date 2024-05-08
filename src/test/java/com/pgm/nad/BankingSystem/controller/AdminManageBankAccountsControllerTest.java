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
public class AdminManageBankAccountsControllerTest extends CommonMvcTest {
    private final String accountId = "1"; // должен быть существуюший аккаунт

    @Test
    @Order(1)
    void manageClientsPage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/accounts"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("accounts"))
                .andExpect(view().name("adminManageBankAccounts/adminManageBankAccounts"));
    }

    @Test
    @Order(2)
    void manageAccountPage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/accounts/account/edit")
                        .param("accountId", accountId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("client"))
                .andExpect(view().name("adminManageBankAccounts/adminManageBankAccount"));
    }

    @Test
    @Order(3)
    void blockAndUnBlockBankAccount() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/accounts/account/block")
                        .param("accountId", accountId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("client"))
                .andExpect(view().name("adminManageBankAccounts/adminManageBankAccount"));
    }

}
