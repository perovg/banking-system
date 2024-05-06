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
}
