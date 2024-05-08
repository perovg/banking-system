package com.pgm.nad.BankingSystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@Transactional
@Rollback(value = false)
public class AdminManageClientsControllerTest extends CommonMvcTest {
    private final String clientId = "1"; //существующий клиент
    private final String accountId = "1"; //существующий клиент
    @Test
    void manageClients() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/clients"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("clients"))
                .andExpect(view().name("adminManageClients/adminManageClients"));
    }

    @Test
    void manageClient() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/clients/client")
                        .param("clientId", clientId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("client"))
                .andExpect(view().name("adminManageClients/adminManageClient"));
    }

    @Test
    void manageClientAccounts() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/clients/client/accounts")
                        .param("clientId", clientId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("client"))
                .andExpect(model().attributeExists("accounts"))
                .andExpect(view().name("adminManageClients/adminManageClientAccounts"));
    }

    @Test
    void manageClientAccount() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/clients/client/accounts/account")
                        .param("accountId", accountId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("client"))
                .andExpect(view().name("adminManageClients/adminManageClientBankAccount"));
    }

    @Test
    void blockAndUnBlockBankAccount() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/clients/client/accounts/account/block")
                        .param("accountId", accountId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("client"))
                .andExpect(view().name("adminManageClients/adminManageClientBankAccount"));
    }
}
