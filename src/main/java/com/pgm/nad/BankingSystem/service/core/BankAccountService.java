package com.pgm.nad.BankingSystem.service.core;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.model.BankAccount;
import com.pgm.nad.BankingSystem.service.core.exceptions.BankAccountServiceException;
import com.pgm.nad.BankingSystem.service.core.exceptions.ClientServiceException;


import java.util.List;

/**
 * The interface in which work with bank accounts is implemented: create, top up, withdraw, transfer etc.
 */
public interface BankAccountService {
    /**
     * A method that extracts a list of all bank accounts from the database
     * and converts them into a list of data transfer objects (DTOs)
     * for further processing by the controller.
     *
     * @return List<BankAccountDto> - all bank accounts in the system.
     */
    List<BankAccountDto> findAll();

    /**
     * A method that extracts a list of all bank accounts for client from the database
     * and converts them into a list of data transfer objects (DTOs)
     * for further processing by the controller.
     *
     * @param clientId - the ID of the client whose list of accounts needs to be extracted.
     * @return List<BankAccountDto> - a list of accounts that belong to this client.
     * @throws BankAccountServiceException - if client with this client id is not in database.
     */
    List<BankAccountDto> findAllByClient(long clientId) throws BankAccountServiceException, ClientServiceException;

    /**
     * A method that extracts lists of customer accounts from the database that he has with a particular bank
     * and transforms this list into a Data Transfer Object (DTO) for further transmission to the controller.
     *
     * @param clientId - the ID of the client whose account needs to be extracted;
     * @param bankId - the ID of the bank for which the account is located and which is being searched.
     * @return List<BankAccountDto> - all the client's accounts that he has in this bank.
     * @throws BankAccountServiceException - if client with this ID is not found or
     *                                       bank with this ID is not found.
     * @throws ClientServiceException - if client with this ID is not found.
     */
    List<BankAccountDto> findAllByClientAndBank(long clientId, long bankId) throws BankAccountServiceException, ClientServiceException;

    /**
     * The method retrieves the bank account information from the database using its ID,
     * converts it into a Data Transfer Object (DTO),
     * and then transfers it to the controller for further processing.
     *
     * @param id - the bank account id that is being searched for.
     * @return BankAccountDto - DTO of the found account.
     * @throws BankAccountServiceException - if bank account with this ID is not in database.
     */
    BankAccountDto findBankAccountDtoById(long id) throws BankAccountServiceException;

    /**
     * The process for creating a bank account involves
     * generating a unique identifier (a 16-digit number,
     * with the first four digits representing the ID of the bank where the account is being created).
     * The missing information is then filled in,
     * and the new account is stored in the database.
     *
     * @param bankAccount - template for a new bank account with partially filled in fields.
     * @param clientId - The ID of the client who creates the account
     * @param bankId - ID of the bank where the account is being created.
     * @throws BankAccountServiceException - if client with this ID is not in database or
     *                                       if bank with this ID is not in database.
     * @throws ClientServiceException - if client with this ID is not in database.
     */
    void create(BankAccount bankAccount, long clientId, long bankId) throws BankAccountServiceException, ClientServiceException;

    /**
     * The method in which all accounts that are in this bank are deleted.
     * Called when the bank is deleted.
     *
     * @param bank - The bank, whose accounts will be deleted. Not null.
     * @throws BankAccountServiceException - if bank is null;
     */
    void deleteAllByBank(Bank bank) throws BankAccountServiceException;

    /**
     * The method of replenishment of the bank account.
     *
     * @param bankAccountId - id of the account to be topped up.
     * @param sum - the amount of replenishment of the bank account
     * @return true - if the operation is completed;
     *         false - if the bank account is not in database or sum <= 0.
     */
    boolean topUp(long bankAccountId, double sum);

    /**
     * The method of withdrawing funds from the account.
     *
     * @param bankAccountId - the ID of the account from which the money will be withdrawn.
     * @param sum = the amount which will be withdrawn.
     * @return true - if the operation is completed;
     *         false - if the bank account is not in database or sum <= 0.
     */
    boolean withdraw(long bankAccountId, double sum);

    /**
     * The method in which funds are transferred from one account to another
     * by the recipient's account number.
     *
     * @param bankAccountFromId - sender's bank account ID;
     * @param bankAccountToId - recipient's bank account ID;
     * @param sum - transfer amount.
     * @return true - if the operation is completed;
     *         false - if the recipient's bank account is not in database or
     *                    the sender's bank account is not in database or
     *                    sum <= 0.
     */
    boolean transfer(long bankAccountFromId, long bankAccountToId, double sum);

    /**
     * The method of deleting a bank account by ID.
     *
     * @param bankAccountId - bank account ID.
     * @return true - if the account has been deleted
     *         false - if the account balance is not 0.
     * @throws BankAccountServiceException - if bank account with this ID is not in database.
     */
    boolean deleteById(long bankAccountId) throws BankAccountServiceException;

    /**
     * The method for blocking a bank account by ID.
     *
     * @param bankAccountId - ID of the account to be blocked/unblocked
     * @return BankAccountDto - DTO of the account after blocking/unblocking.
     * @throws BankAccountServiceException - if bank account with this ID is not in database.
     */
    BankAccountDto blockAndUnblock(long bankAccountId) throws BankAccountServiceException;

    /**
     * The method in which the re-opening of the deposit is implemented.
     *
     * @param accountId ID of the deposit account to re-opening.
     * @throws BankAccountServiceException - if bank account with this ID is not in database or
     *                                       bank account type is not deposit.
     */
    void reopenDepositAccount(long accountId) throws BankAccountServiceException;
}
