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
@RequestMapping("/admin/clients")
@RequiredArgsConstructor
public class AdminManageClientsController {
    private final ClientService clientService;
    private final BankAccountService bankAccountService;

    @GetMapping("")
    public String manageClients(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "adminManageClients/adminManageClients";
    }

    @PostMapping("/client")
    public String manageClient(@RequestParam("clientId") long clientId, Model model) {
        model.addAttribute("client", clientService.findClientDtoById(clientId));
        return "adminManageClients/adminManageClient";
    }

    @PostMapping("/client/accounts")
    public String manageClientAccounts(@RequestParam("clientId") long clientId, Model model) {
        model.addAttribute("client", clientService.findClientDtoById(clientId));
        model.addAttribute("accounts", bankAccountService.findAllByClient(clientId));
        return "adminManageClients/adminManageClientAccounts";
    }

    @PostMapping("/client/accounts/account")
    public String manageClientAccount(@RequestParam("accountId") long accountId, Model model) {
        BankAccountDto bankAccount = bankAccountService.findBankAccountDtoById(accountId);
        model.addAttribute("account", bankAccount);
        model.addAttribute("client", clientService.findClientDtoById(bankAccount.clientId()));
        return "adminManageClients/adminManageClientBankAccount";
    }

    @PostMapping("/client/accounts/account/block")
    public String blockAndUnBlockBankAccount(@RequestParam("accountId") long accountId, Model model) {
        BankAccountDto bankAccount = bankAccountService.blockAndUnblock(accountId);
        model.addAttribute("account", bankAccount);
        model.addAttribute("client", clientService.findClientDtoById(bankAccount.clientId()));
        return "adminManageClients/adminManageClientBankAccount";
    }
}
