package com.pgm.nad.BankingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class Bank {
    @Id
    long bankId;
    String name;
    double interestCreditRate;
    int creditLimit;
    int creditPeriod;
    int depositPeriod;
    double interestDepositRate;
    public Bank(long bankId, String name, double interestCreditRate, int creditLimit, int creditPeriod, int depositPeriod, double interestDepositRate) {
        this.bankId = bankId;
        this.name = name;
        this.interestCreditRate = interestCreditRate;
        this.creditLimit = creditLimit;
        this.creditPeriod = creditPeriod;
        this.depositPeriod = depositPeriod;
        this.interestDepositRate = interestDepositRate;
    }
}
