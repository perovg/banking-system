package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.ClientId;
import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.repository.ClientRepository;
import com.pgm.nad.BankingSystem.service.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/")
@SessionAttributes("clientId")
public class RegistrationController {

    public final ClientRepository clientRepository;

    @Autowired
    public RegistrationController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @ModelAttribute(name = "clientId")
    public ClientId clienId() {return new ClientId();}

    /*@ModelAttribute(name = "newClient")
    public ClientDto createClient() {
        return new ClientDto(Ы);
    }*/

    @GetMapping("/signIn")
    public String signInForm() {return "signInForm";}

    @PostMapping("/signIn")
    public String checkSignIn(ClientId clientId,
                               SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        boolean clientIn = clientRepository.existsById(clientId.getClientId());
        if (clientIn) {
            return "redirect:/banks";
        }
        return "signInForm";
    }

    @GetMapping("/registration")
    public String registration() {return "registrationForm";}

}
