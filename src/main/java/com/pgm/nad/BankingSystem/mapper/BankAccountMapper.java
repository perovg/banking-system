package com.pgm.nad.BankingSystem.mapper;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.model.BankAccount;
import com.pgm.nad.BankingSystem.model.CreditBankAccount;
import com.pgm.nad.BankingSystem.model.DebitBankAccount;
import com.pgm.nad.BankingSystem.model.DepositBankAccount;
import com.pgm.nad.BankingSystem.model.Type;

import java.util.List;

public interface BankAccountMapper {
    default BankAccountDto modelToDto(BankAccount bankAccount) {
        if (bankAccount.getType().equals(Type.CREDIT)) {
            return this.creditBankAccountToDto((CreditBankAccount) bankAccount);
        } else if (bankAccount.getType().equals(Type.DEBIT)) {
            return this.debitBankAccountToDto((DebitBankAccount) bankAccount);
        } else {
            return this.depositBankAccountToDto((DepositBankAccount) bankAccount);
        }
    }

    BankAccountDto depositBankAccountToDto(DepositBankAccount debitBankAccount);

    BankAccountDto debitBankAccountToDto(DebitBankAccount debitBankAccount);

    BankAccountDto creditBankAccountToDto(CreditBankAccount creditBankAccount);

    DebitBankAccount BankAccountToDebitBankAccount(BankAccount bankAccount);

    DepositBankAccount BankAccountToDepositBankAccount(BankAccount bankAccount);

    CreditBankAccount BankAccountToCreditBankAccount(BankAccount bankAccount);

    List<BankAccountDto> toListDto(List<BankAccount> bankAccounts);
}
