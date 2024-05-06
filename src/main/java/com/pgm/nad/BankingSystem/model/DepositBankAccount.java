package com.pgm.nad.BankingSystem.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@RequiredArgsConstructor
public class DepositBankAccount extends BankAccount {
    private long openTime;
    private double interestRate;
    private int period;
    private long periodEnd;
    private boolean completed;

    public DepositBankAccount(BankAccount bankAccount) {
        this.setBankAccountId(bankAccount.getBankAccountId());
        this.setBank(bankAccount.getBank());
        this.setClient(bankAccount.getClient());
        this.setBalance(bankAccount.getBalance());
        this.setType(bankAccount.getType());
        this.setBlocked(bankAccount.isBlocked());

        Bank bank = bankAccount.getBank();
        this.setPeriod(bank.getDepositPeriod());
        this.setInterestRate(bank.getInterestDepositRate());
        long openTime = new Date().getTime() / 1000;
        this.setOpenTime(openTime);
        this.setPeriodEnd(openTime + bank.getDepositPeriod());
    }
}