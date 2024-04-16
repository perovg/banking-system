package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.service.BankAccountServiceImpl;
import com.pgm.nad.BankingSystem.service.BankServiceImpl;
import com.pgm.nad.BankingSystem.service.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ClientController {

    private final ClientServiceImpl clientService;
    public final BankServiceImpl bankService;
    public final BankAccountServiceImpl bankAccountService;

    @PostMapping("/banks/add_bank/create_account")
    public String createAccountInNewBank(@RequestParam("clientId") long clientId,
                                         @RequestParam("bankId") long bankId,
                                         Model model) {
        model.addAttribute("client", clientService.findClientDtoById(clientId));
        model.addAttribute("bank", bankService.findBankDtoById(bankId));
        model.addAttribute("account", new BankAccountDto());
        return "createAccountInNewBank";
    }

    @PostMapping("/banks/add_bank")
    public String addBank(@RequestParam("clientId") long clientId, Model model) {
        model.addAttribute("client", clientService.findClientDtoById(clientId));
        model.addAttribute("banks", bankService.findAll());
        return "addBankForm";
    }

    @PostMapping("/banks/add_bank/create_account/success")
    public String successOfCreationBankAccountInNewBank(@RequestParam("clientId") long clientId,
                                                        @RequestParam("bankId") long bankId,
                                                        BankAccountDto bankAccount) {
        System.out.println(clientId + " " + bankId);
        bankAccountService.save(bankAccount, clientId, bankId);
        return "successOfCreationBankAccountInNewBank";
    }

    @PostMapping("/banks")
    public String mainCatalog(@RequestParam("clientId") long clientId, Model model) {
        ClientDto client = clientService.findClientDtoById(clientId);
        model.addAttribute("client", client);
        model.addAttribute("banks", bankAccountService.findAllBankForClient(client));
        return "mainClientCatalog";
    }

    @PostMapping("/main_catalog")
    public String checkSignIn(@RequestParam("clientId") String clientId, Model model) {
        if (clientService.checkId(clientId)) {
            long clientLongId = Long.parseLong(clientId);
            if (clientLongId == 700000000) {
                return "redirect:/admin";
            }
            ClientDto client = clientService.findClientDtoById(clientLongId);
            model.addAttribute("client", client);
            model.addAttribute("banks", bankAccountService.findAllBankForClient(client));
            return "mainClientCatalog";
        }
        return "redirect:/signIn";
    }
}
