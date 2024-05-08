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
class ClientManagePersonalInformationControllerTest extends CommonMvcTest {
    private final String clientId = "1"; //existing client id
    private final String bankId = "1"; //existing bank id

    @Test
    @Order(1)
    void clientInformation() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/info")
                        .param("clientId", clientId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManagePersonalInformation/clientInfo"));
    }

    @Test
    @Order(2)
    void clientChangeInformation() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/info/updateForm")
                        .param("clientId", clientId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManagePersonalInformation/clientUpdateInfo"));
    }

    @Test
    @Order(3)
    void updateInfo() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/info/update")
                        .param("clientId", clientId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("client"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("clientManagePersonalInformation/clientInfo"));
    }
}