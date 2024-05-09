package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.service.core.BankAccountService;
import com.pgm.nad.BankingSystem.service.core.exceptions.BankAccountServiceException;
import com.pgm.nad.BankingSystem.service.core.ClientService;
import com.pgm.nad.BankingSystem.service.core.exceptions.ClientServiceException;
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
    public String manageClient(@RequestParam("clientId") long clientId, Model model)
            throws ClientServiceException
    {
        model.addAttribute("client", clientService.findClientDtoById(clientId));
        return "adminManageClients/adminManageClient";
    }

    @PostMapping("/client/accounts")
    public String manageClientAccounts(@RequestParam("clientId") long clientId, Model model)
            throws BankAccountServiceException, ClientServiceException
    {
        model.addAttribute("client", clientService.findClientDtoById(clientId));
        model.addAttribute("accounts", bankAccountService.findAllByClient(clientId));
        return "adminManageClients/adminManageClientAccounts";
    }

    @PostMapping("/client/accounts/account")
    public String manageClientAccount(@RequestParam("accountId") long accountId, Model model)
            throws BankAccountServiceException, ClientServiceException
    {
        BankAccountDto bankAccount = bankAccountService.findBankAccountDtoById(accountId);
        model.addAttribute("account", bankAccount);
        model.addAttribute("client", clientService.findClientDtoById(bankAccount.clientId()));
        return "adminManageClients/adminManageClientBankAccount";
    }

    @PostMapping("/client/accounts/account/block")
    public String blockAndUnBlockBankAccount(@RequestParam("accountId") long accountId, Model model)
            throws BankAccountServiceException, ClientServiceException
    {
        BankAccountDto bankAccount = bankAccountService.blockAndUnblock(accountId);
        model.addAttribute("account", bankAccount);
        model.addAttribute("client", clientService.findClientDtoById(bankAccount.clientId()));
        return "adminManageClients/adminManageClientBankAccount";
    }
}
