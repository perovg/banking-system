package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.model.Client;
import com.pgm.nad.BankingSystem.repository.ClientRepository;
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
        model.addAttribute("clientId", "");
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

    private boolean checkId(String clientId){
        if (clientId.length() != 9) {
            return false;
        }
        boolean flag = true;
        for (int i = 0; i < 9; i++) {
            char sym = clientId.charAt(i);
            if (i == 0 && sym != '7') {
                flag = false;
            }
            if (!Character.isDigit(sym)) {
                flag = false;
            }
        }
        return flag;
    }

    @PostMapping("/signIn")
    public String checkSignIn(@ModelAttribute("clientId") String clientId) {
        boolean clientIn = checkId(clientId);
        if (clientIn) {
            long clientLongId = Long.parseLong(clientId);
            clientIn = clientRepository.existsById(clientLongId);
            if (clientIn) {
                if (clientLongId == 700000000) {
                    return "redirect:/admin";
                }
                return "redirect:/banks";
            }
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
