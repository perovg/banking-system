package com.pgm.nad.BankingSystem.dto;

import com.pgm.nad.BankingSystem.model.BankAccount;
import java.util.HashSet;

public record BankDto(
        int bankId,
        String name,
        double interestRate,
        HashSet<BankAccount> bankAccounts
) {}
