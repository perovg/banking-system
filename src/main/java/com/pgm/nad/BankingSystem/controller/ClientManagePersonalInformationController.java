package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.dto.ClientDto;
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

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ClientManagePersonalInformationController {
    private final ClientServiceImpl clientService;
    public final BankServiceImpl bankService;
    public final BankAccountServiceImpl bankAccountService;

    @PostMapping("/info")
    public String clientInformation(@RequestParam("clientId") long clientId, Model model) {
        model.addAttribute("client", clientService.findClientDtoById(clientId));
        return "clientManagePersonalInformation/clientInfo";
    }

    @PostMapping("/info/updateForm")
    public String clientChangeInformation(@RequestParam("clientId") long clientId, Model model) {
        model.addAttribute("client", clientService.findClientDtoById(clientId));
        return "clientManagePersonalInformation/clientUpdateInfo";
    }

    @PostMapping("/info/update")
    public String updateInfo(ClientDto client, Model model) {
        if (client.getName().isEmpty() || client.getSurname().isEmpty()) {
            model.addAttribute("client", clientService.findClientDtoById(client.getClientId()));
            return "clientManagePersonalInformation/clientUpdateInfo";
        }

        clientService.update(client);
        model.addAttribute("client", client);
        return "clientManagePersonalInformation/clientInfo";
    }
}
