package com.pgm.nad.BankingSystem.mapper;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.model.BankAccount;
import com.pgm.nad.BankingSystem.model.CreditBankAccount;
import com.pgm.nad.BankingSystem.model.DebitBankAccount;
import com.pgm.nad.BankingSystem.model.DepositBankAccount;
import java.util.List;

public interface BankAccountMapper {
    default BankAccountDto modelToDto(BankAccount bankAccount) {
        switch (bankAccount.getType()) {
            case DEBIT -> {
                return this.debitBankAccountToDto((DebitBankAccount) bankAccount);
            }
            case CREDIT -> {
                return this.creditBankAccountToDto((CreditBankAccount) bankAccount);
            }
            case DEPOSIT -> {
                return this.depositBankAccountToDto((DepositBankAccount) bankAccount);
            }
        }
        return null;
    }

    BankAccountDto depositBankAccountToDto(DepositBankAccount debitBankAccount);

    BankAccountDto debitBankAccountToDto(DebitBankAccount debitBankAccount);

    BankAccountDto creditBankAccountToDto(CreditBankAccount creditBankAccount);

    DebitBankAccount BankAccountToDebitBankAccount(BankAccount bankAccount);

    DepositBankAccount BankAccountToDepositBankAccount(BankAccount bankAccount);

    CreditBankAccount BankAccountToCreditBankAccount(BankAccount bankAccount);

    List<BankAccountDto> BankAccountListToBankAccountDtoList(List<BankAccount> bankAccounts);
}
