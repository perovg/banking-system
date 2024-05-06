package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.model.BankAccount;

import java.util.List;

public interface BankAccountService {
    List<BankAccountDto> findAll();

    List<BankAccountDto> findAllByClient(long clientId);

    List<BankAccountDto> findAllByClientAndBank(long client, long bank);

    BankAccountDto findBankAccountDtoById(long id);

    void create(BankAccount bankAccount, long clientId, long bankId);

    void deleteAllByBank(Bank bank);

    boolean withdraw(long bankAccountId, double sum);

    boolean transfer(long bankAccountFromId, long bankAccountToId, double sum);

    boolean deleteById(long bankAccountId);

    BankAccountDto blockAndUnblock(long bankAccountId);

    void reopenDepositAccount(long accountId);
}
