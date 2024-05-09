package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.model.BankAccount;
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
public class ClientCreateBankAccountController {
    private final ClientService clientService;
    public final BankService bankService;
    public final BankAccountService bankAccountService;

    @PostMapping("/add_bank")
    public String addBank(@RequestParam("clientId") long clientId, Model model) {
        ClientDto client = clientService.findClientDtoById(clientId);
        model.addAttribute("client", client);
        model.addAttribute("banks", bankService.findAllNewBanks(client));
        return "clientCreateAccounts/clientAddBank";
    }

    @PostMapping("/add_bank/create_account")
    public String createAccountInNewBank
            (@RequestParam("clientId") long clientId,
             @RequestParam("bankId") long bankId,
             Model model
            ) throws BankIsNotFoundException {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bank", bankService.findBankDtoById(bankId));
        model.addAttribute("account", new BankAccount());
        return "clientCreateAccounts/clientCreateBankAccount";
    }

    @PostMapping("/accounts/create/conditions")
    public String conditions(
            @RequestParam("clientId") long clientId,
            @RequestParam("bankId") long bankId,
            Model model
    ) throws BankIsNotFoundException {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bank", bankService.findBankDtoById(bankId));
        return "clientCreateAccounts/clientCreateBankAccountConditions";
    }

    @PostMapping("/add_bank/create_account/success")
    public String successOfCreationBankAccountInNewBank(
            @RequestParam("clientId") long clientId,
            @RequestParam("bankId") long bankId,
            BankAccount bankAccount,
            Model model
    ) throws BankIsNotFoundException {
        bankAccountService.create(bankAccount, clientId, bankId);
        model.addAttribute("clientId", clientId);
        model.addAttribute("bank", bankService.findBankDtoById(bankId));
        return "clientCreateAccounts/clientCreateBankAccountSuccess";
    }
}
