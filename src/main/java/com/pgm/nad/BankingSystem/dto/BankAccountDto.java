package com.pgm.nad.BankingSystem.dto;

import com.pgm.nad.BankingSystem.model.Type;
import lombok.Data;

@Data
public class BankAccountDto {
    private long bankAccountId;
    private Type type;
    private double balance;
    private boolean confirmed;
    private Long clientId;
    private Long bankId;
}
