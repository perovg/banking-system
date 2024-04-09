package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.repository.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Slf4j
@Controller
@RequestMapping("/")
public class AdminController {

    public final BankRepository bankRepository;
    public Random random = new Random();

    @Autowired
    public AdminController(BankRepository bankRepository) {this.bankRepository = bankRepository;}

    @GetMapping("/admin")
    public String mainAdminPage() {
        return "mainAdminPage";
    }

    @GetMapping("/admin/banks")
    public String manageBanks(Model model) {
        model.addAttribute("banks", bankRepository.findAll());
        return "manageBanksForm";
    }

    @GetMapping("/admin/banks/create")
    public String createBank(Model model) {
        model.addAttribute("bank", new Bank());
        return "createBankForm";
    }

    @PostMapping("/admin/banks/create")
    public String checkCreatingBank(Bank bank) {
        long id = random.nextLong(1000, 9999);
        while (bankRepository.existsById(id)) {
            id = random.nextLong(1000, 9999);
        }
        bank.setBankId(id);
        bankRepository.save(bank);
        return "redirect:/admin/banks";
    }

    @GetMapping("/admin/banks/{id}/edit")
    public String manageBank(@PathVariable long id, Model model) {
        Bank bank = bankRepository.findByBankId(id);
        model.addAttribute("bank", bank);
        return "manageBankForm";
    }

    @PostMapping("/admin/banks/{id}/edit")
    public String updateBank(@PathVariable("id") long id,
                             @RequestParam double interestRate,
                             @RequestParam int creditLimit) {
        Bank bank = bankRepository.findByBankId(id);
        bank.setInterestRate(interestRate);
        bank.setCreditLimit(creditLimit);
        bankRepository.save(bank);
        return "redirect:/admin/banks";
    }
}
