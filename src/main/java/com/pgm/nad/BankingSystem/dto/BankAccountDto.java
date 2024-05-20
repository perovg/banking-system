package com.pgm.nad.BankingSystem.dto;

import com.pgm.nad.BankingSystem.model.CreditBankAccount;
import com.pgm.nad.BankingSystem.model.DebitBankAccount;
import com.pgm.nad.BankingSystem.model.DepositBankAccount;
import com.pgm.nad.BankingSystem.model.Type;

public record BankAccountDto(
        long bankAccountId,
        Type type,
        double balance,
        boolean blocked,
        long clientId,
        long bankId,
        long openTime,
        double interestRate,
        int period,
        long periodEnd,
        boolean completed
) {
    public static BankAccountDto ifDebit(DebitBankAccount debitBankAccount) {
        if (debitBankAccount == null) {
            return null;
        }

        return new BankAccountDto(
                debitBankAccount.getBankAccountId(),
                debitBankAccount.getType(),
                debitBankAccount.getBalance(),
                debitBankAccount.isBlocked(),
                debitBankAccount.getClient().getClientId(),
                debitBankAccount.getBank().getBankId(),
                0,
                0,
                0,
                0,
                false
        );
    }

    public static BankAccountDto ifCredit(CreditBankAccount creditBankAccount) {
        if (creditBankAccount == null) {
            return null;
        }

        return new BankAccountDto(
                creditBankAccount.getBankAccountId(),
                creditBankAccount.getType(),
                creditBankAccount.getBalance(),
                creditBankAccount.isBlocked(),
                creditBankAccount.getClient().getClientId(),
                creditBankAccount.getBank().getBankId(),
                creditBankAccount.getOpenTime(),
                creditBankAccount.getInterestRate(),
                creditBankAccount.getPeriod(),
                0,
                false
        );
    }

    public static BankAccountDto ifDeposit(DepositBankAccount depositBankAccount) {
        if (depositBankAccount == null) {
            return null;
        }

        return new BankAccountDto(
                depositBankAccount.getBankAccountId(),
                depositBankAccount.getType(),
                depositBankAccount.getBalance(),
                depositBankAccount.isBlocked(),
                depositBankAccount.getClient().getClientId(),
                depositBankAccount.getBank().getBankId(),
                depositBankAccount.getOpenTime(),
                depositBankAccount.getInterestRate(),
                depositBankAccount.getPeriod(),
                depositBankAccount.getPeriodEnd(),
                depositBankAccount.isCompleted()
        );
    }
}
