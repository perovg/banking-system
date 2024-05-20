package com.pgm.nad.BankingSystem.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@RequiredArgsConstructor
public class DebitBankAccount extends BankAccount {
    public DebitBankAccount(BankAccount bankAccount) {
        this.setBankAccountId(bankAccount.getBankAccountId());
        this.setBank(bankAccount.getBank());
        this.setClient(bankAccount.getClient());
        this.setBalance(bankAccount.getBalance());
        this.setType(bankAccount.getType());
        this.setBlocked(bankAccount.isBlocked());
    }
}