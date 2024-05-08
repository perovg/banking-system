package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.service.BankAccountService;
import com.pgm.nad.BankingSystem.service.BankService;
import com.pgm.nad.BankingSystem.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/banks/accounts/account")
@RequiredArgsConstructor
public class ClientManageBankAccountController {
    private final ClientService clientService;
    public final BankService bankService;
    public final BankAccountService bankAccountService;

    @PostMapping("")
    public String bankAccountClientPage(
            @RequestParam("bankId") long bankId,
            @RequestParam("clientId") long clientId,
            @RequestParam("accountId") long accountId,
            Model model
    ) {
        BankAccountDto bankAccount = bankAccountService.findBankAccountDtoById(accountId);
        model.addAttribute("account", bankAccount);
        model.addAttribute("client", clientService.findClientDtoById(clientId));
        model.addAttribute("bank", bankService.findBankDtoById(bankId));
        model.addAttribute("realTime", Math.min(new Date().getTime() / 1000, bankAccount.periodEnd()));
        model.addAttribute("type", bankAccount.type().toString());
        System.out.println(bankAccount.type().toString().equals("DEBIT"));
        return "clientManageBankAccounts/clientManageBankAccount";
    }

    @PostMapping("/topUpForm")
    public String topUpForm(
            @RequestParam("bankId") long bankId,
            @RequestParam("clientId") long clientId,
            @RequestParam("accountId") long accountId,
            Model model
    ) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("accountId", accountId);
        model.addAttribute("amount", 0.0);
        return "clientManageBankAccounts/clientTopUp";
    }

    @PostMapping("/topUp")
    public String topUp(
            @RequestParam("bankId") long bankId,
            @RequestParam("clientId") long clientId,
            @RequestParam("accountId") long accountId,
            @RequestParam("amount") double amount,
            Model model
    ) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("accountId", accountId);
        if (bankAccountService.topUp(accountId, amount)) {
            return "clientManageBankAccounts/clientTopUpSuccess";
        }
        return "clientManageBankAccounts/clientTopUpFail";
    }

    @PostMapping("/withdrawForm")
    public String withdrawForm(
            @RequestParam("bankId") long bankId,
            @RequestParam("clientId") long clientId,
            @RequestParam("accountId") long accountId,
            Model model
    ) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("accountId", accountId);
        model.addAttribute("type", bankAccountService.findBankAccountDtoById(accountId).type().toString());
        if (bankAccountService.findBankAccountDtoById(accountId).type().toString().equals("CREDIT")) {
            model.addAttribute("creditLimit", bankService.findBankDtoById(bankId).creditLimit());
        } else {
            model.addAttribute("creditLimit", 0);
        }
        model.addAttribute("balance", bankAccountService.findBankAccountDtoById(accountId).balance());
        model.addAttribute("amount", 0.0);
        return "clientManageBankAccounts/clientWithdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(
            @RequestParam("bankId") long bankId,
            @RequestParam("clientId") long clientId,
            @RequestParam("accountId") long accountId,
            @RequestParam("amount") double amount,
            Model model
    ) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("accountId", accountId);
        if (bankAccountService.withdraw(accountId, amount)) {
            return "clientManageBankAccounts/clientWithdrawSuccess";
        }
        return "clientManageBankAccounts/clientWithdrawFail";
    }

    @PostMapping("/transferForm")
    public String transferForm(
            @RequestParam("bankId") long bankId,
            @RequestParam("clientId") long clientId,
            @RequestParam("accountId") long accountId,
            Model model
    ) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("accountId", accountId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("type", bankAccountService.findBankAccountDtoById(accountId).type().toString());
        if (bankAccountService.findBankAccountDtoById(accountId).type().toString().equals("CREDIT")) {
            model.addAttribute("creditLimit", bankService.findBankDtoById(bankId).creditLimit());
        } else {
            model.addAttribute("creditLimit", 0);
        }
        model.addAttribute("balance", bankAccountService.findBankAccountDtoById(accountId).balance());
        model.addAttribute("amount", 0.0);
        model.addAttribute("recipientAccountId", 0);
        return "clientManageBankAccounts/clientTransfer";
    }

    @PostMapping("/transfer")
    public String transfer(
            @RequestParam("bankId") long bankId,
            @RequestParam("clientId") long clientId,
            @RequestParam("accountId") long accountId,
            @RequestParam("recipientAccountId") long recipientAccountId,
            @RequestParam("amount") double amount,
            Model model
    ) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("accountId", accountId);
        model.addAttribute("recipientAccountId", recipientAccountId);
        model.addAttribute("amount", amount);
        if (bankAccountService.transfer(accountId, recipientAccountId, amount)) {
            return "clientManageBankAccounts/clientTransferSuccess";
        }
        return "clientManageBankAccounts/clientTransferFail";
    }

    @PostMapping("/reopen")
    public String reopenDepositAccount(
            @RequestParam("accountId") long accountId,
            @RequestParam("clientId") long clientId,
            @RequestParam("bankId") long bankId,
            Model model
    ) {
        bankAccountService.reopenDepositAccount(accountId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("clientId", clientId);
        model.addAttribute("accountId", accountId);
        return "clientManageBankAccounts/clientReopenDepositAccountSuccess";
    }

    @PostMapping("/delete")
    public String clientDeleteAccount(
            @RequestParam("clientId") long clientId,
            @RequestParam("accountId") long accountId,
            @RequestParam("bankId") long bankId,
            Model model
    ) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("accountId", accountId);
        if (bankAccountService.deleteById(accountId)) {
            model.addAttribute("accounts", bankAccountService.findAllByClientAndBank(clientId, bankId));
            return "clientManageBankAccounts/clientDeleteAccountSuccess";
        }
        return "clientManageBankAccounts/clientDeleteBankAccountFail";
    }
}
