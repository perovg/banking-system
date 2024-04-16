package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.service.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AdminManageClientsController {

    private final ClientServiceImpl clientService;

    @GetMapping("/admin/clients")
    public String manageClients(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "manageClientsForm";
    }
}
