package com.pgm.nad.BankingSystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping("/")
public class AdminController {
    @GetMapping("/admin")
    public String mainAdminPage() {
        return "mainAdminPage";
    }

}
