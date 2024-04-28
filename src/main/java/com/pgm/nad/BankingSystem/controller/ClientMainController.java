package com.pgm.nad.BankingSystem.controller;

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
public class ClientMainController {

    private final ClientServiceImpl clientService;
    public final BankServiceImpl bankService;
    public final BankAccountServiceImpl bankAccountService;

    @PostMapping("/banks")
    public String mainCatalog(@RequestParam("clientId") long clientId, Model model) {
        if (clientService.existById(clientId)) {
            if (clientId == 700000000) {
                return "redirect:/admin";
            }
            ClientDto client = clientService.findClientDtoById(clientId);
            model.addAttribute("client", client);
            model.addAttribute("banks", bankService.findAllBankForClient(client));
            return "client/clientBanks";
        }
        return "redirect:/signIn";
    }

    @PostMapping("/banks/accounts")
    public String bankCatalog(@RequestParam("bankId") long bankId, @RequestParam("clientId") long clientId, Model model) {
        model.addAttribute("accounts", bankAccountService.findAllByClientAndBank(clientId, bankId));
        model.addAttribute("clientId", clientId);
        model.addAttribute("bank", bankService.findBankDtoById(bankId));
        return "client/clientBankBankAccounts";
    }
}
