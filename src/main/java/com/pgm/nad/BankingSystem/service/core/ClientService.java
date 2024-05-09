package com.pgm.nad.BankingSystem.service.core;

import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.model.Client;
import com.pgm.nad.BankingSystem.service.core.exceptions.ClientServiceException;

import java.util.List;

/**
 * An interface that allows you to work with clients.
 * This is where clients are saved, updated, extracted and transformed.
 */
public interface ClientService {
    /**
     * A method that retrieves all clients from the database
     * and converts them into DTO (Data Transfer Object) objects,
     * returning a list of the resulting objects.
     *
     * @return List<ClientDto>
     */
    List<ClientDto> findAll();

    /**
     * The method that retrieves a client from the database,
     * converts it to a DTO (Data Transfer Object), and returns the result.
     *
     * @param id - ID of the client to be extracted.
     * @return ClientDto.
     * @throws ClientServiceException - if client with this ID not in database.
     */
    ClientDto findClientDtoById(long id) throws ClientServiceException;

    /**
     * The method to retrieves the client from the database for further modification.
     *
     * @param id - ID of the client to be extracted.
     * @return Client.
     * @throws ClientServiceException - if client with this id is not in database.
     */
    Client findClientById(long id) throws ClientServiceException;

    /**
     * The method in which the new client is saved. At the same time, a unique ID is generated for it.
     *
     * @param client - new client. Not null.
     * @return the ID of the newly created client.
     * @throws ClientServiceException if client is null.
     */
    long save(Client client) throws ClientServiceException;

    /**
     * The method that checks if the client is in the database by id;
     *
     * @param clientId - ID of client.
     * @return true if client is in the database;
     *         false - else.
     */
    boolean existsById(long clientId);

    /**
     * A method for updating client data in the database.
     *
     * @param client - the client object that contains the updated information. Not null.
     * @throws ClientServiceException if client is null.
     */
    void update(Client client) throws ClientServiceException;

    /**
     * A method that checks if a client exists in the database by client ID.
     *
     * @param clientId - each client has a unique ID - a nine-digit number.
     * @return true - if there is a bank with this id in the database;
     *         false - else.
     */
    boolean existById(long clientId);
}
