package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.model.Bank;

import java.util.List;

public interface BankAccountService {
    List<BankAccountDto> findAll();

    List<BankAccountDto> findAllByClient(long clientId);

    List<BankAccountDto> findAllByClientAndBank(long client, long bank);

    BankAccountDto findById(long id);

    void create(BankAccountDto bankAccount);

    void deleteAllByBank(Bank bank);

    boolean withdraw(long bankAccountId, double sum);

    boolean transfer(long bankAccountFromId, long bankAccountToId, double sum);

    boolean deleteById(long bankAccountId);

    BankAccountDto blockAndUnblock(long bankAccountId, boolean isBlocked);

    void reopenDepositAccount(long accountId);
}
