package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class AdminManageClientsController {

    private final ClientRepository clientRepository;

    @Autowired
    public AdminManageClientsController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @GetMapping("/admin/clients")
    public String manageClients(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "manageClientsForm";
    }
}
