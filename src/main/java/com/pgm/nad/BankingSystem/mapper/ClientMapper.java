package com.pgm.nad.BankingSystem.mapper;

import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.model.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client dtoToModel(ClientDto clientDto);

    ClientDto modelToDto(Client client);

    List<ClientDto> ClientListToClientDtoList(List<Client> clients);
}
