package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.dto.BankDto;
import com.pgm.nad.BankingSystem.service.BankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "manageBanksForm";
    }

    @GetMapping("/admin/banks/create")
    public String createBank(Model model) {
        model.addAttribute("bank", new BankDto());
        return "createBankForm";
    }

    @PostMapping("/admin/banks/create")
    public String checkCreatingBank(BankDto bank) {
        bankService.save(bank);
        return "redirect:/admin/banks";
    }

    @GetMapping("/admin/banks/{id}/edit")
    public String manageBank(@PathVariable long id, Model model) {
        model.addAttribute("bank", bankService.findBankDtoById(id));
        return "manageBankForm";
    }

    @PostMapping("/admin/banks/{id}/edit")
    public String updateBank(@PathVariable("id") long id,
                             @RequestParam double interestRate,
                             @RequestParam int creditLimit) {
        bankService.update(id, interestRate, creditLimit);
        return "redirect:/admin/banks";
    }
}
