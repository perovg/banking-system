package com.pgm.nad.BankingSystem.dto;

import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.model.BankAccount;

import java.util.HashSet;


public record ClientDto (
        long clientId,
        HashSet<BankAccount> bankAccounts,
        String name,
        String surname,
        String passport,
        String address,
        HashSet<Bank> banks
) {}
