package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.model.Client;

import java.util.List;

public interface ClientService {

    List<ClientDto> findAll();

    ClientDto findClientDtoById(long id);

    Client findClientById(long id);

    boolean checkId(String clientId);

    long save(ClientDto client);

    boolean existsById(long clientId);

}
