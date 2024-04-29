package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.service.BankAccountServiceImpl;
import com.pgm.nad.BankingSystem.service.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AdminManageBankAccountsController {
    private final BankAccountServiceImpl bankAccountService;
    private final ClientServiceImpl clientService;

    @GetMapping("/admin/accounts")
    public String manageClients(Model model) {
        model.addAttribute("accounts", bankAccountService.findAll());
        return "adminManageBankAccounts/adminManageBankAccounts";
    }

    @PostMapping("/admin/accounts/account/edit")
    public String manageAccountPage(@RequestParam("accountId") long accountId, Model model) {
        BankAccountDto bankAccount = bankAccountService.findById(accountId);
        model.addAttribute("account", bankAccount);
        model.addAttribute("client", clientService.findClientDtoById(bankAccount.clientId()));
        return "adminManageBankAccounts/adminManageBankAccount";
    }

    @PostMapping("/admin/accounts/account/block")
    public String blockAndUnBlockBankAccount(@RequestParam("accountId") long accountId,
                                             Model model) {
        BankAccountDto bankAccount = bankAccountService.blockAndUnblock(accountId);
        model.addAttribute("account", bankAccount);
        model.addAttribute("client", clientService.findClientDtoById(bankAccount.clientId()));
        return "adminManageBankAccounts/adminManageBankAccount";
    }
}
