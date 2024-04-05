package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;

import java.util.List;

public interface BankAccountService {
    List<BankAccountDto> findAll();
    BankAccountDto findById(Long id);
    BankAccountDto save(BankAccountDto bankAccount);
    void deleteById(Long id);
}
