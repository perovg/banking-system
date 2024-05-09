package com.pgm.nad.BankingSystem.service.impl;

import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.mapper.ClientMapper;
import com.pgm.nad.BankingSystem.model.Client;
import com.pgm.nad.BankingSystem.repository.ClientRepository;
import com.pgm.nad.BankingSystem.service.core.ClientService;
import com.pgm.nad.BankingSystem.service.core.NullClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;
    private final Random random = new Random();
    private long id = 700000000;

    @Override
    public List<ClientDto> findAll() {
        return clientMapper.ClientListToClientDtoList(clientRepository.findAll());
    }

    @Override
    public ClientDto findClientDtoById(long id) {
        return clientRepository.findById(id).map(clientMapper::modelToDto).get();
    }

    @Override
    public Client findClientById(long id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public long save(Client client) throws NullClientException {
        if (client == null) {
            throw new NullClientException("Client is null");
        }
        long clientId = generateClientId();
        client.setClientId(clientId);
        clientRepository.save(client);
        return clientId;
    }

    @Override
    public boolean existsById(long clientId) {
        return clientRepository.existsById(clientId);
    }

    private long generateClientId() {
        while (existsById(id) || id == 700000000) {
            StringBuilder clientId = new StringBuilder("7");
            for (int i = 0; i < 8; i++) {
                String symbol = Integer.toString(random.nextInt(0, 10));
                clientId.append(symbol);
            }
            id = Long.parseLong(clientId.toString());
        }
        return id;
    }

    @Override
    public void update(Client client) throws NullClientException {
        if (client == null) {
            throw new NullClientException("Client is null");
        }
        clientRepository.save(client);
    }

    @Override
    public boolean existById(long clientId) {
        return clientRepository.existsById(clientId);
    }
}
