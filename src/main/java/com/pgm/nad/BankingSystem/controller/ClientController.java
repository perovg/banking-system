package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.model.Client;
import com.pgm.nad.BankingSystem.repository.ClientRepository;
import com.pgm.nad.BankingSystem.service.ClientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Controller
@RequestMapping("/")
public class ClientController {

    private final ClientServiceImpl clientService;
    public final ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientServiceImpl clientService,
                            ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/banks")
    public String mainCatalog(@RequestParam("clientId") String clientId, Model model) {

        return "mainClientCatalog";
    }

    @PostMapping("/main_catalog")
    public String checkSignIn(@RequestParam("clientId") String clientId, Model model) {
        boolean clientIn = checkId(clientId);
        if (clientIn) {
            long clientLongId = Long.parseLong(clientId);
            clientIn = clientRepository.existsById(clientLongId);
            if (clientIn) {
                if (clientLongId == 700000000) {
                    return "redirect:/admin";
                }
                Client client = clientService.getById(clientLongId);
                model.addAttribute("client", client);
                //model.addAttribute("banks", clientService.findBanksForClient(client));
                return "mainClientCatalog";
            }
        }
        return "redirect:/signIn";
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
}
