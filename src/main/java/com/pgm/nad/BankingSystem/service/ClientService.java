package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.model.Client;

import java.util.List;
import java.util.Set;

public interface ClientService {
    List<Bank> findBanksForClient(Client client);
    List<ClientDto> findAll();
    ClientDto findById(Long id);
    ClientDto save(ClientDto client);
}
