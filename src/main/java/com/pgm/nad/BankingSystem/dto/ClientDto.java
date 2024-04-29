package com.pgm.nad.BankingSystem.dto;

public record ClientDto(
        long clientId,
        String name,
        String surname,
        String passport,
        String address
) {
}
