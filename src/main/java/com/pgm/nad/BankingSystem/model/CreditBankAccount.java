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

    public CreditBankAccount(BankAccount bankAccount) {
        this.setBankAccountId(bankAccount.getBankAccountId());
        this.setBank(bankAccount.getBank());
        this.setClient(bankAccount.getClient());
        this.setBalance(bankAccount.getBalance());
        this.setType(bankAccount.getType());
        this.setBlocked(bankAccount.isBlocked());

        Bank bank = bankAccount.getBank();
        this.setPeriod(bank.getCreditPeriod());
        this.setInterestRate(bank.getInterestCreditRate());
    }
}
