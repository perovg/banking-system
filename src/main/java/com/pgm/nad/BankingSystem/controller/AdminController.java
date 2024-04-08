package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.repository.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class AdminController {

    public final BankRepository bankRepository;

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

}
