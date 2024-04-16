package com.pgm.nad.BankingSystem.dto;

import lombok.Data;

@Data
public class ClientDto {
    long clientId;
    String name;
    String surname;
    String passport;
    String address;
}
