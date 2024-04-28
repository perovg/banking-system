package com.pgm.nad.BankingSystem.dto;

import com.pgm.nad.BankingSystem.model.Type;
import lombok.Data;

import java.util.Date;

@Data
public class BankAccountDto {
    private long bankAccountId;
    private Type type;
    private double balance;
    private boolean blocked;
    private long clientId;
    private long bankId;
    private long openTime;
    private double interestRate;
    private int period;
    private long periodEnd;
    private boolean completed;
}
