package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.BankDto;
import com.pgm.nad.BankingSystem.dto.ClientDto;

import java.util.ArrayList;
import java.util.List;

public interface BankService {
    BankDto findBankDtoById(long bankId);

    void update(long bankId,
                String name,
                int creditPeriod,
                double interestCreditRate,
                int creditLimit,
                int depositPeriod,
                double interestDepositRate);

    List<BankDto> findAll();

    void save(BankDto bank);

    boolean existsById(long bankId);

    boolean existsByName(String name);

    ArrayList<BankDto> findAllBankForClient(ClientDto client);

    ArrayList<BankDto> findAllNewBanks(ClientDto client);

    void deleteById(long bankId);
}
