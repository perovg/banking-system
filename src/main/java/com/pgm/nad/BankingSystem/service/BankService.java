package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.BankDto;
import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.model.Bank;

import java.util.ArrayList;
import java.util.List;

public interface BankService {
    BankDto findBankDtoById(long bankId);

    void update(Bank bank);

    List<BankDto> findAll();

    void save(Bank bank);

    boolean existsById(long bankId);

    boolean existsByName(String name);

    Bank findBankById(long bankId);

    ArrayList<BankDto> findAllBankForClient(ClientDto client);

    ArrayList<BankDto> findAllNewBanks(ClientDto client);

    void deleteById(long bankId);
}
