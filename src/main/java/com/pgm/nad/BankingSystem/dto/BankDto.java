package com.pgm.nad.BankingSystem.dto;

import lombok.Data;

@Data
public class BankDto {
    private long bankId;
    private String name;
    private double interestRate;
    private int creditLimit;
}
