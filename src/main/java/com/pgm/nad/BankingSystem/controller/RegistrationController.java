package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.ClientId;
import com.pgm.nad.BankingSystem.model.Client;
import com.pgm.nad.BankingSystem.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Random;

@Slf4j
@Controller
@RequestMapping("/")
@SessionAttributes("clientId")
public class RegistrationController {
    public final ClientRepository clientRepository;

    public String message = "";
    public Random random = new Random();
    long id = 700000000;

    @ModelAttribute(name = "id")
    public long id() {
        return id;
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

    @Autowired
    public RegistrationController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @ModelAttribute(name = "check")
    public ClientId clientId() {
        return new ClientId();
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
    public String signInForm() {
        return "signInForm";
    }


    @PostMapping("/signIn")
    public String checkSignIn(ClientId clientId,
                              SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        boolean clientIn = clientRepository.existsById(clientId.getClientCheckId());
        if (clientIn) {
            return "redirect:/banks";
        }
        return "signInForm";
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
