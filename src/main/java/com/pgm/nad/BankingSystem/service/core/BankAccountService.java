package com.pgm.nad.BankingSystem.service.core;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.model.BankAccount;

import java.util.List;

public interface BankAccountService {

    /**
     * A method that extracts a list of all bank accounts from the database
     * and converts them into a list of data transfer objects (DTOs)
     * for further processing by the controller.
     *
     * @return List<BankAccountDto>
     */
    List<BankAccountDto> findAll();

    List<BankAccountDto> findAllByClient(long clientId);

    List<BankAccountDto> findAllByClientAndBank(long client, long bank);

    BankAccountDto findBankAccountDtoById(long id);

    void create(BankAccount bankAccount, long clientId, long bankId);

    void deleteAllByBank(Bank bank);

    boolean topUp(long bankAccountId, double sum);

    boolean withdraw(long bankAccountId, double sum);

    boolean transfer(long bankAccountFromId, long bankAccountToId, double sum);

    boolean deleteById(long bankAccountId);

    BankAccountDto blockAndUnblock(long bankAccountId);

    void reopenDepositAccount(long accountId);
}
