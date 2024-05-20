package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.model.Client;
import com.pgm.nad.BankingSystem.service.core.ClientService;
import com.pgm.nad.BankingSystem.service.core.exceptions.ClientServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RegistrationController {
    public final ClientService clientService;

    @GetMapping
    public String bankingSystemMain() {
        return "redirect:/signIn";
    }

    @GetMapping("/signIn")
    public String signInForm(Model model) {
        model.addAttribute("clientId", 0);
        return "signInAndSignUp/signInForm";
    }

    @GetMapping("/signUp")
    public String registration(Model model) {
        model.addAttribute("client", new Client());
        return "signInAndSignUp/registrationForm";
    }

    @PostMapping("/signUp")
    public String successRegister(Client client, Model model) throws ClientServiceException {
        if (client.getName().isEmpty() || client.getSurname().isEmpty()) {
            return "signInAndSignUp/signUpError";
        }

        model.addAttribute("id", clientService.save(client));
        return "signInAndSignUp/signUpSuccess";
    }
}
