package com.pgm.nad.BankingSystem.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminControllerTest extends CommonMvcTest {
    @Test
    void mainCatalog() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("admin/admin"));
    }
}
