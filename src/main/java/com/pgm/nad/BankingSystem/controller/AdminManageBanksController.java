package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.service.core.exceptions.BankAccountServiceException;
import com.pgm.nad.BankingSystem.service.core.BankService;
import com.pgm.nad.BankingSystem.service.core.exceptions.BankServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/banks")
@RequiredArgsConstructor
public class AdminManageBanksController {
    public final BankService bankService;

    @GetMapping("")
    public String manageBanks(Model model) {
        model.addAttribute("banks", bankService.findAll());
        return "adminManageBanks/adminManageBanks";
    }

    @GetMapping("/create")
    public String createBank(Model model) {
        model.addAttribute("bank", new Bank());
        return "adminManageBanks/adminCreateBank";
    }

    @PostMapping("/create")
    public String checkCreatingBank(Bank bank) throws BankServiceException {
        System.out.println(bank.getInterestDepositRate());
        bankService.save(bank);
        return "redirect:/admin/banks";
    }

    @PostMapping("/bank/edit")
    public String manageBank(@RequestParam("bankId") long bankId, Model model) throws BankServiceException {
        model.addAttribute("bank", bankService.findBankById(bankId));
        return "adminManageBanks/adminManageBank";
    }

    @PostMapping("/bank")
    public String updateBank(Bank bank) {
        bankService.update(bank);
        return "redirect:/admin/banks";
    }

    @PostMapping("/bank/delete")
    public String deleteBank(@RequestParam("bankId") long bankId) throws BankAccountServiceException {
        bankService.deleteById(bankId);
        return "redirect:/admin/banks";
    }
}
