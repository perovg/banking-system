package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.mapper.ClientMapper;
import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.model.Client;
import com.pgm.nad.BankingSystem.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    @Override
    public List<ClientDto> findAll() {return clientMapper.toListDto(clientRepository.findAll());}

    @Override
    public List<Bank> findBanksForClient(Client client) {
        return clientRepository.findBanksByClientId(client.getClientId());
    }
    @Override
    public ClientDto findById(Long id) {
        return Optional.of(getById(id)).map(clientMapper::modelToDto).get();
    }

    @Override
    @Transactional
    public ClientDto save(ClientDto client) {
        return clientMapper.modelToDto(clientRepository.save(
                clientMapper.dtoToModel(client)));
    }


    public Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Client with bankId: " + id + " not found"));
    }

}
