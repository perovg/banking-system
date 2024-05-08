package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Files;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@Transactional
@Rollback(value = false)
class RegistrationControllerTest extends CommonMvcTest {
    private Client newGoodClientDto = new Client(
            12345,
            "Test_name",
            "Test_surname",
            "AB123456",
            "123 Main Street"
    );
    private Client newBadClientDto = new Client(
            12345,
            null,
            null,
            "",
            ""
    );

    @BeforeEach
    public void prepareData() {

    }

    @Test
    void bankingSystemMain() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/signIn"));
    }

    @Test
    void signInForm() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/signIn"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("client"))
                .andExpect(model().attribute("client", new Client()))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("signInAndSignUp/signInForm"));
    }

    @Test
    void registration() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/signUp"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("client"))
                .andExpect(model().attribute("client", new Client()))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("signInAndSignUp/registrationForm"));
    }

    @Test
    void successRegister() throws Exception {
        File login = new ClassPathResource("templates/signUpSuccess.html").getFile();
        String html = new String(Files.readAllBytes(login.toPath()));
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("client", newGoodClientDto)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("signInAndSignUp/signUpSuccess"))
                .andReturn();
    }

    @Test
    void unSuccessRegister() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("client", newBadClientDto)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("signInAndSignUp/signUpError"));
    }
}