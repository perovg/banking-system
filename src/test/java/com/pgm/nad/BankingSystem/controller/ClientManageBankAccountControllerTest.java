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
class ClientManageBankAccountControllerTest extends CommonMvcTest {
    private final String clientId = "1"; //existing client id
    private final String bankId = "1"; //existing bank id
    private final String accountId = "1"; //existing accountId

    @Test
    @Order(1)
    void bankAccountClientPage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/accounts/account")
                        .param("bankId", bankId)
                        .param("clientId", clientId)
                        .param("accountId", accountId)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attributeExists("client"))
                .andExpect(model().attributeExists("bank"))
                .andExpect(model().attributeExists("realTime"))
                .andExpect(model().attributeExists("type"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManageBankAccounts/clientManageBankAccount"));
    }

    @Test
    @Order(1)
    void topUpForm() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/accounts/account/topUpForm")
                        .param("bankId", bankId)
                        .param("clientId", clientId)
                        .param("accountId", accountId)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bankId"))
                .andExpect(model().attributeExists("accountId"))
                .andExpect(model().attributeExists("amount"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManageBankAccounts/clientTopUp"));
    }

    @Test
    @Order(2)
    void topUpSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/accounts/account/topUpForm")
                        .param("bankId", bankId)
                        .param("clientId", clientId)
                        .param("accountId", accountId)
                        .param("amount", "23")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bankId"))
                .andExpect(model().attributeExists("accountId"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManageBankAccounts/clientTopUpSuccess"));
    }

    @Test
    @Order(3)
    void topUpFail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/accounts/account/topUp")
                        .param("bankId", bankId)
                        .param("clientId", clientId)
                        .param("accountId", accountId)
                        .param("amount", "23212313")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bankId"))
                .andExpect(model().attributeExists("accountId"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManageBankAccounts/clientTopUpFail"));
    }

    @Test
    @Order(4)
    void withdrawForm() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/accounts/account/withdrawForm")
                        .param("bankId", bankId)
                        .param("clientId", clientId)
                        .param("accountId", accountId)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bankId"))
                .andExpect(model().attributeExists("accountId"))
                .andExpect(model().attributeExists("type"))
                .andExpect(model().attributeExists("creditLimit"))
                .andExpect(model().attributeExists("balance"))
                .andExpect(model().attributeExists("amount"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManageBankAccounts/clientWithdraw"));
    }

    @Test
    @Order(5)
    void withdrawSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/accounts/account/withdraw")
                        .param("bankId", bankId)
                        .param("clientId", clientId)
                        .param("accountId", accountId)
                        .param("amount", "123")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bankId"))
                .andExpect(model().attributeExists("accountId"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManageBankAccounts/clientWithdrawSuccess"));
    }

    @Test
    @Order(5)
    void withdrawFail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/accounts/account/withdraw")
                        .param("bankId", bankId)
                        .param("clientId", clientId)
                        .param("accountId", accountId)
                        .param("amount", "131231233212113")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bankId"))
                .andExpect(model().attributeExists("accountId"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManageBankAccounts/clientWithdrawFail"));
    }

    @Test
    @Order(6)
    void transferForm() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/accounts/account/transferForm")
                        .param("bankId", bankId)
                        .param("clientId", clientId)
                        .param("accountId", accountId)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bankId"))
                .andExpect(model().attributeExists("accountId"))
                .andExpect(model().attributeExists("type"))
                .andExpect(model().attributeExists("creditLimit"))
                .andExpect(model().attributeExists("balance"))
                .andExpect(model().attributeExists("amount"))
                .andExpect(model().attributeExists("recipientAccountId"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManageBankAccounts/clientTransfer"));
    }

    @Test
    @Order(7)
    void transferSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/accounts/account/transfer")
                        .param("bankId", bankId)
                        .param("clientId", clientId)
                        .param("accountId", accountId)
                        .param("recipientAccountId", accountId)
                        .param("amount", "23")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bankId"))
                .andExpect(model().attributeExists("accountId"))
                .andExpect(model().attributeExists("recipientAccountId"))
                .andExpect(model().attributeExists("amount"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManageBankAccounts/clientTransferSuccess"));
    }

    @Test
    @Order(8)
    void transferFail() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/accounts/account/transfer")
                        .param("bankId", bankId)
                        .param("clientId", clientId)
                        .param("accountId", accountId)
                        .param("recipientAccountId", accountId)
                        .param("amount", "123123123213123")
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bankId"))
                .andExpect(model().attributeExists("accountId"))
                .andExpect(model().attributeExists("recipientAccountId"))
                .andExpect(model().attributeExists("amount"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManageBankAccounts/clientTransferFail"));
    }

    @Test
    @Order(9)
    void reopen() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/accounts/account/reopen")
                        .param("accountId", accountId)
                        .param("clientId", clientId)
                        .param("bankId", bankId)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bankId"))
                .andExpect(model().attributeExists("accountId"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManageBankAccounts/clientReopenDepositAccountSuccess"));
    }

    @Test
    @Order(10)
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/banks/accounts/account/delete")
                        .param("clientId", clientId)
                        .param("accountId", accountId)
                        .param("bankId", bankId)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("clientId"))
                .andExpect(model().attributeExists("bankId"))
                .andExpect(model().attributeExists("accountId"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManageBankAccounts/clientDeleteAccountSuccess"));
    }
}