package com.pgm.nad.BankingSystem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@Transactional
@Rollback(value = false)
class ClientControllerTest extends CommonMvcTest {
    private final String adminId = "700000000";
    private final String userId = "729467976";
    private final String notExistUserId = "1312312312313123";

    @Test
    void mainCatalog() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/banks")
                        .param("clientId", adminId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("mainClientCatalog"));
    }

    @Test
    void checkSignInAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/main_catalog")
                        .param("clientId", adminId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"))
                .andExpect(model().errorCount(0));
    }

    @Test
    void checkSignInUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/main_catalog")
                        .param("clientId", userId)) // нужно вставить айди пользователя существующего
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("mainClientCatalog"))
                .andExpect(model().errorCount(0));
    }

    @Test
    void checkSignInNotExistingUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/main_catalog")
                        .param("clientId", notExistUserId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/signIn"))
                .andExpect(model().errorCount(0));
    }
}
