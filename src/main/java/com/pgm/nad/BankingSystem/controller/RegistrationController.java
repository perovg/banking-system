package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.model.Client;
import com.pgm.nad.BankingSystem.repository.ClientRepository;
import com.pgm.nad.BankingSystem.service.ClientService;
import com.pgm.nad.BankingSystem.service.ClientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Slf4j
@Controller
@RequestMapping("/")
public class RegistrationController {
    public final ClientRepository clientRepository;

    public Random random = new Random();
    long id = 700000000;

    @ModelAttribute(name = "id")
    public long id() {
        return id;
    }

    @Autowired
    public RegistrationController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @ModelAttribute(name = "client")
    public Client createClient() {
        return new Client();
    }

    @GetMapping
    public String bankingSystemMain() {
        return "redirect:/signIn";
    }

    @GetMapping("/signIn")
    public String signInForm(Model model) {
        model.addAttribute("client", new Client());
        return "signInForm";
    }

    public long newClientId() {
        while (clientRepository.existsById(id)) {
            StringBuilder clientId = new StringBuilder("7");
            for (int i = 0; i < 8; i++) {
                String symbol = Integer.toString(random.nextInt(0, 10));
                clientId.append(symbol);
            }
            id = Long.parseLong(clientId.toString());
        }
        return id;
    }

    @GetMapping("/signUp")
    public String registration() {
        return "registrationForm";
    }

    @PostMapping("/signUp")
    public String successRegister(Client client) {
        if (client.getName().isEmpty() || client.getSurname().isEmpty()) {
            return "redirect:/signUp/error";
        }

        client.setClientId(newClientId());
        clientRepository.save(client);
        return "redirect:/signUp/success";
    }

    @GetMapping("/signUp/error")
    public String signUpError() {
        return "signUpError";
    }

    @GetMapping("/signUp/success")
    public String signUpSuccess() {
        return "signUpSuccess";
    }

}
