package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.service.core.BankAccountService;
import com.pgm.nad.BankingSystem.service.core.BankIsNotFoundException;
import com.pgm.nad.BankingSystem.service.core.BankService;
import com.pgm.nad.BankingSystem.service.core.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/banks")
@RequiredArgsConstructor
public class ClientMainController {
    private final ClientService clientService;
    public final BankService bankService;
    public final BankAccountService bankAccountService;

    @PostMapping("")
    public String mainCatalog(@RequestParam("clientId") long clientId, Model model) {
        if (clientId == 700000000) {
            return "redirect:/admin";
        }
        if (clientService.existById(clientId)) {
            ClientDto client = clientService.findClientDtoById(clientId);
            model.addAttribute("client", client);
            model.addAttribute("banks", bankService.findAllBankForClient(client));
            return "client/clientBanks";
        }
        return "redirect:/signIn";
    }

    @PostMapping("/accounts")
    public String bankCatalog(
            @RequestParam("bankId") long bankId,
            @RequestParam("clientId") long clientId,
            Model model
    ) throws BankIsNotFoundException {
        model.addAttribute("accounts", bankAccountService.findAllByClientAndBank(clientId, bankId));
        model.addAttribute("clientId", clientId);
        model.addAttribute("bank", bankService.findBankDtoById(bankId));
        return "client/clientBankBankAccounts";
    }
}
