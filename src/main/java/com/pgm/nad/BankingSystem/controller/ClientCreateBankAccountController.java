package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.model.BankAccount;
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
public class ClientCreateBankAccountController {
    private final ClientServiceImpl clientService;
    public final BankServiceImpl bankService;
    public final BankAccountServiceImpl bankAccountService;

    @PostMapping("/banks/add_bank")
    public String addBank(@RequestParam("clientId") long clientId, Model model) {
        ClientDto client = clientService.findClientDtoById(clientId);
        model.addAttribute("client", client);
        model.addAttribute("banks", bankService.findAllNewBanks(client));
        return "clientCreateAccounts/clientAddBank";
    }

    @PostMapping("/banks/add_bank/create_account")
    public String createAccountInNewBank(@RequestParam("clientId") long clientId,
                                         @RequestParam("bankId") long bankId,
                                         Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bank", bankService.findBankDtoById(bankId));
        model.addAttribute("account", new BankAccount());
        return "clientCreateAccounts/clientCreateBankAccount";
    }

    @PostMapping("/banks/accounts/create/conditions")
    public String conditions(@RequestParam("clientId") long clientId,
                             @RequestParam("bankId") long bankId,
                             Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bank", bankService.findBankDtoById(bankId));
        return "clientCreateAccounts/clientCreateBankAccountConditions";
    }

    @PostMapping("/banks/add_bank/create_account/success")
    public String successOfCreationBankAccountInNewBank(@RequestParam("clientId") long clientId,
                                                        @RequestParam("bankId") long bankId,
                                                        BankAccount bankAccount,
                                                        Model model) {
        bankAccountService.create(bankAccount, clientId, bankId);
        model.addAttribute("clientId", clientId);
        model.addAttribute("bank", bankService.findBankDtoById(bankId));
        return "clientCreateAccounts/clientCreateBankAccountSuccess";
    }
}
