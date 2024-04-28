package com.pgm.nad.BankingSystem.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@RequiredArgsConstructor
public class CreditBankAccount extends BankAccount {
    private long openTime;
    private double interestRate;
    private int period;
}
