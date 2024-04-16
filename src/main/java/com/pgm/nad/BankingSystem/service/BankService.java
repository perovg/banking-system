package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.BankDto;
import com.pgm.nad.BankingSystem.model.Bank;

import java.util.List;

public interface BankService {
    BankDto findByName(String name);

    BankDto findBankDtoById(long bankId);

    Bank findBankById(long bankId);

    List<BankDto> findAll();

    boolean save(BankDto bank);

    void update(long bankId, double interestRate, int creditLimit);

    boolean existsById(long bankId);

    boolean existByName(String name);

    void deleteById(long bankId);

}
