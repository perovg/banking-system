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
public class AdminManageClientsController {
    private final ClientServiceImpl clientService;
    private final BankAccountServiceImpl bankAccountService;

    @GetMapping("/admin/clients")
    public String manageClients(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "adminManageClients/adminManageClients";
    }

    @PostMapping("admin/clients/client")
    public String manageClient(@RequestParam("clientId") long clientId, Model model) {
        model.addAttribute("client", clientService.findClientDtoById(clientId));
        return "adminManageClients/adminManageClient";
    }

    @PostMapping("admin/clients/client/accounts")
    public String manageClientAccounts(@RequestParam("clientId") long clientId, Model model) {
        model.addAttribute("client", clientService.findClientDtoById(clientId));
        model.addAttribute("accounts", bankAccountService.findAllByClient(clientId));
        return "adminManageClients/adminManageClientAccounts";
    }

    @PostMapping("/admin/clients/client/accounts/account")
    public String manageClientAccount(@RequestParam("accountId") long accountId, Model model) {
        BankAccountDto bankAccount = bankAccountService.findById(accountId);
        model.addAttribute("account", bankAccount);
        model.addAttribute("client", clientService.findClientDtoById(bankAccount.getClientId()));
        return "adminManageClients/adminManageClientBankAccount";
    }

    @PostMapping("/admin/clients/client/accounts/account/block")
    public String blockAndUnBlockBankAccount(@RequestParam("accountId") long accountId,
                                             @RequestParam("isBlocked") boolean isBlocked,
                                             Model model) {
        BankAccountDto bankAccount = bankAccountService.blockAndUnblock(accountId, isBlocked);
        model.addAttribute("account", bankAccount);
        model.addAttribute("client", clientService.findClientDtoById(bankAccount.getClientId()));
        return "adminManageClients/adminManageClientBankAccount";
    }
}
