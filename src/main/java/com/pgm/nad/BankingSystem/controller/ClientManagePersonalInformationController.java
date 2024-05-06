package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.model.Client;
import com.pgm.nad.BankingSystem.service.BankAccountService;
import com.pgm.nad.BankingSystem.service.BankService;
import com.pgm.nad.BankingSystem.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/info")
@RequiredArgsConstructor
public class ClientManagePersonalInformationController {
    private final ClientService clientService;
    public final BankService bankService;
    public final BankAccountService bankAccountService;

    @PostMapping("")
    public String clientInformation(@RequestParam("clientId") long clientId, Model model) {
        model.addAttribute("client", clientService.findClientDtoById(clientId));
        return "clientManagePersonalInformation/clientInfo";
    }

    @PostMapping("/updateForm")
    public String clientChangeInformation(@RequestParam("clientId") long clientId, Model model) {
        model.addAttribute("client", clientService.findClientById(clientId));
        return "clientManagePersonalInformation/clientUpdateInfo";
    }

    @PostMapping("/update")
    public String updateInfo(Client client, Model model) {
        if (client.getName().isEmpty() || client.getSurname().isEmpty()) {
            model.addAttribute("client", clientService.findClientById(client.getClientId()));
            return "clientManagePersonalInformation/clientUpdateInfo";
        }

        clientService.update(client);
        model.addAttribute("client", clientService.findClientDtoById(client.getClientId()));
        return "clientManagePersonalInformation/clientInfo";
    }
}
