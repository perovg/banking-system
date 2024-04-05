package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.model.Client;

import java.util.List;

public interface ClientService {
    List<ClientDto> findAll();
    ClientDto findById(Long id);
    ClientDto save(ClientDto client);
}
