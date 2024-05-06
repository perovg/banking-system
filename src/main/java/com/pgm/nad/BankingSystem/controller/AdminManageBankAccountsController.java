package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.service.BankAccountService;
import com.pgm.nad.BankingSystem.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/accounts")
@RequiredArgsConstructor
public class AdminManageBankAccountsController {
    private final BankAccountService bankAccountService;
    private final ClientService clientService;

    @GetMapping("")
    public String manageClientsPage(Model model) {
        model.addAttribute("accounts", bankAccountService.findAll());
        return "adminManageBankAccounts/adminManageBankAccounts";
    }

    @PostMapping("/account/edit")
    public String manageAccountPage(@RequestParam("accountId") long accountId, Model model) {
        BankAccountDto bankAccount = bankAccountService.findBankAccountDtoById(accountId);
        model.addAttribute("account", bankAccount);
        model.addAttribute("client", clientService.findClientDtoById(bankAccount.clientId()));
        return "adminManageBankAccounts/adminManageBankAccount";
    }

    @PostMapping("/account/block")
    public String blockAndUnBlockBankAccount(@RequestParam("accountId") long accountId, Model model) {
        BankAccountDto bankAccount = bankAccountService.blockAndUnblock(accountId);
        model.addAttribute("account", bankAccount);
        model.addAttribute("client", clientService.findClientDtoById(bankAccount.clientId()));
        return "adminManageBankAccounts/adminManageBankAccount";
    }
}
