package com.pgm.nad.BankingSystem.dto;

import lombok.Data;

@Data
public class BankDto {
    private long bankId;
    private String name;
    private double interestCreditRate;
    private int creditLimit;
    private int creditPeriod;
    private int depositPeriod;
    private double interestDepositRate;
}
