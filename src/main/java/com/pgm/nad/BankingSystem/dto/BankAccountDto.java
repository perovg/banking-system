package com.pgm.nad.BankingSystem.dto;

import com.pgm.nad.BankingSystem.model.Type;

public record BankAccountDto(
        long bankAccountId,
        Type type,
        double balance,
        boolean blocked,
        long clientId,
        long bankId,
        long openTime,
        double interestRate,
        int period,
        long periodEnd,
        boolean completed
) {
}
