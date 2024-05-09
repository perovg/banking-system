package com.pgm.nad.BankingSystem.service.core;

import com.pgm.nad.BankingSystem.dto.BankDto;
import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.model.Bank;

import java.util.List;

/**
 * An interface that allows you to work with banks.
 * This is where banks are saved, updated, extracted and transformed.
 */
public interface BankService {
    /**
     * A method for extracting a bank from the database
     * and converting it into a Data Transfer Object (DTO)
     * for subsequent transfer to the controller using the bank's ID.
     *
     * @param bankId - each bank has a unique ID - a four-digit number.
     * @return BankDto - if there is a bank with this id in the database;
     * @throws BankIsNotFoundException if a bank with this id not in database.
     */
    BankDto findBankDtoById(long bankId) throws BankIsNotFoundException;

    /**
     * A method for updating the bank data in the database by the administrator.
     *
     * @param bank - the bank that will be updated. Not null.
     */
    void update(Bank bank);

    /**
     * A method for retrieving a list of all banks stored in the database
     * and converting it into a list of DTOs
     * for further processing and transmission to the controller.
     *
     * @return List<BankDto>;
     */
    List<BankDto> findAll();

    /**
     * A method for saving a new bank created by the administrator to the database.
     * In this method, a four-digit id is generated for the bank.
     *
     * @param bank - the bank that needs to be saved. Not null.
     */
    void save(Bank bank) throws NullBankNameException;

    /**
     * A method that checks if a bank exists in the database by bank id.
     *
     * @param bankId - each bank has a unique ID - a four-digit number.
     * @return true - if there is a bank with this id in the database;
     *         false - else.
     */
    boolean existsById(long bankId);

    /**
     * A method that checks if a bank exists in the database by name.
     *
     * @param name - each bank has a unique name. Not null.
     * @return true - if there is a bank with this name in the database;
     *         false - else.
     * @throws NullBankNameException if name is null.
     */
    boolean existsByName(String name) throws NullBankNameException;

    /**
     * A method that extracts the bank from the database by bank id
     * for further modification of its parameters by the administrator.
     *
     * @param bankId - each bank has a unique ID - a four-digit number. Not null.
     * @return Bank - if there is a bank with this id in the database;
     * @throws BankIsNotFoundException if a bank with this id not in database.
     */
    Bank findBankById(long bankId) throws BankIsNotFoundException;

    /**
     * A method that retrieves for the client a list of banks in which he has accounts
     * and converts it into a list of Data Transfer Objects (DTO) for further transmission to the controller.
     *
     * @param client - ClientDto. Not null.
     * @return List<BankDto> - if there is the client in the database;
     *         null - else.
     */
    List<BankDto> findAllBankForClient(ClientDto client);

    /**
     * A method that retrieves for the client a list of banks in which he hasn't accounts
     * and converts it into a list of Data Transfer Objects (DTO) for further transmission to the controller.
     *
     * @param client - ClientDto. Not null.
     * @return List<BankDto> - if there is the client in the database;
     *         null - else.
     */
    List<BankDto> findAllNewBanks(ClientDto client);

    /**
     * A method that removes the bank and all accounts associated with it from the database by bank id.
     *
     * @param bankId - each bank has a unique ID - a four-digit number. Not null.
     */
    void deleteById(long bankId);
}
