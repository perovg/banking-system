package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.model.Client;

import java.util.List;

public interface BankAccountService {
    List<BankAccountDto> findAll();

    List<BankAccountDto> findAllByClientAndBank(Client client, Bank bank);

    BankAccountDto findById(Long id);

    List<Bank> findAllBankForClient(ClientDto client);

    void save(BankAccountDto bankAccount, long clientId, long bankId);

    BankAccountDto save(BankAccountDto bankAccount);

    void deleteById(Long id);

    void deleteAllByBank(Bank bank);
}
