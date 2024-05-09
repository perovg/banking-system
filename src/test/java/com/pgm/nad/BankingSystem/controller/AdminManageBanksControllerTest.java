package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.model.Bank;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@Transactional
@Rollback(value = false)
public class AdminManageBanksControllerTest extends CommonMvcTest {
    private static Bank bank;
    private final String bankId = "3519"; // существующий банк должен быть

    @BeforeAll
    void prepareData() {
        bank = new Bank(1000, "Test Bank", 4.0, 10000, 1000, 1000, 1000.0);
    }

    @Test
    void manageBanks() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/banks"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("banks"))
                .andExpect(view().name("adminManageBanks/adminManageBanks"));
    }

    @Test
    void createBank() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/banks/create"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("bank"))
                .andExpect(view().name("adminManageBanks/adminCreateBank"));
    }

    @Test
    void checkCreatingBank() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/banks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("bank", bank))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/admin/banks"));
    }

    @Test
    void manageBank() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/banks/bank/edit")
                        .param("bankId", bankId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("bank"))
                .andExpect(view().name("adminManageBanks/adminManageBank"));
    }

    @Test
    void updateBank() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/banks/bank") //existing id of bank
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("bank", bank))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/admin/banks"));
    }

    @Test
    void deleteBank() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/banks/bank/delete") //existing id of bank
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("bankId", bankId))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/admin/banks"));
    }
}
