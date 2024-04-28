package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.dto.BankDto;
import com.pgm.nad.BankingSystem.service.BankService;
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
public class AdminManageBanksController {
    public final BankService bankService;

    @GetMapping("/admin/banks")
    public String manageBanks(Model model) {
        model.addAttribute("banks", bankService.findAll());
        return "adminManageBanks/adminManageBanks";
    }

    @GetMapping("/admin/banks/create")
    public String createBank(Model model) {
        model.addAttribute("bank", new BankDto());
        return "adminManageBanks/adminCreateBank";
    }

    @PostMapping("/admin/banks/create")
    public String checkCreatingBank(BankDto bank) {
        System.out.println(bank.getInterestDepositRate());
        bankService.save(bank);
        return "redirect:/admin/banks";
    }

    @PostMapping("/admin/banks/bank/edit")
    public String manageBank(@RequestParam("bankId") long bankId, Model model) {
        model.addAttribute("bank", bankService.findBankDtoById(bankId));
        return "adminManageBanks/adminManageBank";
    }

    @PostMapping("/admin/banks/bank")
    public String updateBank(@RequestParam("bankId") long bankId,
                             @RequestParam String name,
                             @RequestParam int creditPeriod,
                             @RequestParam double interestCreditRate,
                             @RequestParam int creditLimit,
                             @RequestParam int depositPeriod,
                             @RequestParam double interestDepositRate) {
        bankService.update(bankId, name, creditPeriod, interestCreditRate, creditLimit, depositPeriod, interestDepositRate);
        return "redirect:/admin/banks";
    }
    @PostMapping("/admin/banks/bank/delete")
    public String deleteBank(@RequestParam("bankId") long bankId) {
        bankService.deleteById(bankId);
        return "redirect:/admin/banks";
    }
}
