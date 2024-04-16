package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.service.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RegistrationController {
    public final ClientServiceImpl clientService;

    @GetMapping
    public String bankingSystemMain() {
        return "redirect:/signIn";
    }

    @GetMapping("/signIn")
    public String signInForm(Model model) {
        model.addAttribute("client", new ClientDto());
        return "signInForm";
    }

    @GetMapping("/signUp")
    public String registration(Model model) {
        model.addAttribute("client", new ClientDto());
        return "registrationForm";
    }

    @PostMapping("/signUp")
    public String successRegister(ClientDto client, Model model) {
        if (client.getName().isEmpty() || client.getSurname().isEmpty()) {
            return "signUpError";
        }

        model.addAttribute("id", clientService.save(client));
        return "signUpSuccess";
    }
}
