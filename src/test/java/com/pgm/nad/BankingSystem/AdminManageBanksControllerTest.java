package com.pgm.nad.BankingSystem;

import com.pgm.nad.BankingSystem.model.Bank;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@Transactional
@Rollback(value = false)
public class AdminManageBanksControllerTest extends CommonMvcTest {
    private Bank bank;

    @BeforeAll
    void prepareData() {
        bank = new Bank(1000, "Test Bank", 4d, 10000, new ArrayList<>());
    }

    @Test
    void mainCatalog() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/banks"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("banks"))
                .andExpect(view().name("manageBanksForm"));
    }

    @Test
    void createBank() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/banks/create"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("bank"))
                .andExpect(view().name("createBankForm"));
    }

    @Test
    void addBank() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/banks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("bank", bank))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/admin/banks"));
    }

    @Test
    void manageBank() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/banks/{id}/edit", "1328")) // need to take existing bank id
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(model().attributeExists("bank"))
                .andExpect(view().name("manageBankForm"));
    }

    @Test
    void updateBank() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/banks/{id}/edit", "1328") //existing id of bank
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("interestRate", "3")
                        .param("creditLimit", "20000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("redirect:/admin/banks"));
    }
}
