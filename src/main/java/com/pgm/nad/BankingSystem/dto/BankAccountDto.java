package com.pgm.nad.BankingSystem.dto;



public record BankAccountDto(

        long bankAccountId,
        String type,
        double balance,
        boolean confirmed
) {}
