package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
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

import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ClientManageBankAccountController {
    private final ClientServiceImpl clientService;
    public final BankServiceImpl bankService;
    public final BankAccountServiceImpl bankAccountService;

    @PostMapping("/banks/accounts/account")
    public String bankAccountClientPage(@RequestParam("bankId") long bankId,
                                        @RequestParam("clientId") long clientId,
                                        @RequestParam("accountId") long accountId,
                                        Model model) {
        BankAccountDto bankAccount = bankAccountService.findById(accountId);
        model.addAttribute("account", bankAccount);
        model.addAttribute("client", clientService.findClientDtoById(clientId));
        model.addAttribute("bank", bankService.findBankDtoById(bankId));
        model.addAttribute("realTime", Math.min(new Date().getTime() / 1000, bankAccount.getPeriodEnd()));
        model.addAttribute("type", bankAccount.getType().toString());
        System.out.println(bankAccount.getType().toString().equals("DEBIT"));
        return "clientManageBankAccounts/clientManageBankAccount";
    }

    @PostMapping("/banks/accounts/account/topUpForm")
    public String topUpForm(@RequestParam("bankId") long bankId,
                            @RequestParam("clientId") long clientId,
                            @RequestParam("accountId") long accountId,
                            Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("accountId", accountId);
        model.addAttribute("amount", 0.0);
        return "clientManageBankAccounts/clientTopUp";
    }

    @PostMapping("/banks/accounts/account/topUp")
    public String topUp(@RequestParam("bankId") long bankId,
                        @RequestParam("clientId") long clientId,
                        @RequestParam("accountId") long accountId,
                        @RequestParam("amount") double amount,
                        Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("accountId", accountId);
        if (bankAccountService.topUp(accountId, amount)) {
            return "clientManageBankAccounts/clientTopUpSuccess";
        }
        return "clientManageBankAccounts/clientTopUpFail";
    }

    @PostMapping("/banks/accounts/account/withdrawForm")
    public String withdrawForm(@RequestParam("bankId") long bankId,
                               @RequestParam("clientId") long clientId,
                               @RequestParam("accountId") long accountId,
                               Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("accountId", accountId);
        model.addAttribute("type", bankAccountService.findById(accountId).getType().toString());
        if (bankAccountService.findById(accountId).getType().toString().equals("CREDIT")) {
            model.addAttribute("creditLimit", bankService.findBankDtoById(bankId).getCreditLimit());
        } else {
            model.addAttribute("creditLimit", 0);
        }
        model.addAttribute("balance", bankAccountService.findById(accountId).getBalance());
        model.addAttribute("amount", 0.0);
        return "clientManageBankAccounts/clientWithdraw";
    }

    @PostMapping("/banks/accounts/account/withdraw")
    public String withdraw(@RequestParam("bankId") long bankId,
                           @RequestParam("clientId") long clientId,
                           @RequestParam("accountId") long accountId,
                           @RequestParam("amount") double amount,
                           Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("accountId", accountId);
        if (bankAccountService.withdraw(accountId, amount)) {
            return "clientManageBankAccounts/clientWithdrawSuccess";
        }
        return "clientManageBankAccounts/clientWithdrawFail";
    }

    @PostMapping("/banks/accounts/account/transferForm")
    public String transferForm(@RequestParam("bankId") long bankId, @RequestParam("clientId") long clientId, @RequestParam("accountId") long accountId, Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("accountId", accountId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("type", bankAccountService.findById(accountId).getType().toString());
        if (bankAccountService.findById(accountId).getType().toString().equals("CREDIT")) {
            model.addAttribute("creditLimit", bankService.findBankDtoById(bankId).getCreditLimit());
        } else {
            model.addAttribute("creditLimit", 0);
        }
        model.addAttribute("balance", bankAccountService.findById(accountId).getBalance());
        model.addAttribute("amount", 0.0);
        model.addAttribute("recipientAccountId", 0);
        return "clientManageBankAccounts/clientTransfer";
    }

    @PostMapping("/banks/accounts/account/transfer")
    public String transfer(@RequestParam("bankId") long bankId,
                           @RequestParam("clientId") long clientId,
                           @RequestParam("accountId") long accountId,
                           @RequestParam("recipientAccountId") long recipientAccountId,
                           @RequestParam("amount") double amount,
                           Model model) {
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

    @PostMapping("banks/accounts/account/reopen")
    public String reopenDepositAccount(@RequestParam("accountId") long accountId,
                                       @RequestParam("clientId") long clientId,
                                       @RequestParam("bankId") long bankId,
                                       Model model) {
        bankAccountService.reopenDepositAccount(accountId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("clientId", clientId);
        model.addAttribute("accountId", accountId);
        return "clientManageBankAccounts/clientReopenDepositAccountSuccess";
    }

    @PostMapping("banks/accounts/account/delete")
    public String clientDeleteAccount(@RequestParam("clientId") long clientId,
                                      @RequestParam("accountId") long accountId,
                                      @RequestParam("bankId") long bankId,
                                      Model model) {
        model.addAttribute("clientId", clientId);
        model.addAttribute("bankId", bankId);
        model.addAttribute("accountId", accountId);
        model.addAttribute("accounts", bankAccountService.findAllByClientAndBank(clientId, bankId));
        if (bankAccountService.deleteById(accountId)) {
            return "clientManageBankAccounts/clientDeleteAccountSuccess";
        }
        return "clientManageBankAccounts/clientDeleteBankAccountFail";
    }
}
