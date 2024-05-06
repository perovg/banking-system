package com.pgm.nad.BankingSystem.dto;

public record BankDto(
        long bankId,
        String name,
        double interestCreditRate,
        int creditLimit,
        int creditPeriod,
        int depositPeriod,
        double interestDepositRate
) {
}
