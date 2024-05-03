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
    @Test
    void mainCatalog() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/clients"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("clients"))
                .andExpect(view().name("manageClientsForm"));
    }
}

