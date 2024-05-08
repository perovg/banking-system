package com.pgm.nad.BankingSystem.service;

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
     * A method for extracting a bank account from the database
     * and converting it into a Data Transfer Object (DTO)
     * for subsequent transfer to the controller using the bank's ID.
     *
     * @param bankId;
     * @return BankDto;
     */
    BankDto findBankDtoById(long bankId);

    /**
     * A method for updating the bank data in the database by the administrator.
     *
     * @param bank;
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
     * @param bank;
     */
    void save(Bank bank);

    /**
     * A method that checks if a bank exists in the database by bank id.
     *
     * @param bankId;
     * @return boolean;
     */
    boolean existsById(long bankId);

    /**
     * A method that checks if a bank exists in the database by name.
     *
     * @param name;
     * @return boolean;
     */
    boolean existsByName(String name);

    /**
     * A method that extracts the bank from the database by bank id
     * for further modification of its parameters by the administrator.
     *
     * @param bankId;
     * @return Bank;
     */
    Bank findBankById(long bankId);

    /**
     * A method that retrieves for the client a list of banks in which he has accounts
     * and converts it into a list of Data Transfer Objects (DTO) for further transmission to the controller.
     *
     * @param client;
     * @return List<BankDto>;
     */
    List<BankDto> findAllBankForClient(ClientDto client);

    /**
     * A method that retrieves for the client a list of banks in which he hasn't accounts
     * and converts it into a list of Data Transfer Objects (DTO) for further transmission to the controller.
     *
     * @param client;
     * @return List<BankDto>
     */
    List<BankDto> findAllNewBanks(ClientDto client);

    /**
     * A method that removes the bank and all accounts associated with it from the database by bank id.
     *
     * @param bankId;
     */
    void deleteById(long bankId);
}
