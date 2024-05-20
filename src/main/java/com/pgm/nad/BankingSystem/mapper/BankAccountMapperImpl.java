package com.pgm.nad.BankingSystem.mapper;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.model.BankAccount;
import com.pgm.nad.BankingSystem.model.CreditBankAccount;
import com.pgm.nad.BankingSystem.model.DebitBankAccount;
import com.pgm.nad.BankingSystem.model.DepositBankAccount;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BankAccountMapperImpl implements BankAccountMapper {

    @Override
    public BankAccountDto depositBankAccountToDto(DepositBankAccount depositBankAccount) {
        return BankAccountDto.ifDeposit(depositBankAccount);
    }

    @Override
    public BankAccountDto debitBankAccountToDto(DebitBankAccount debitBankAccount) {
        return BankAccountDto.ifDebit(debitBankAccount);
    }

    @Override
    public BankAccountDto creditBankAccountToDto(CreditBankAccount creditBankAccount) {
        return BankAccountDto.ifCredit(creditBankAccount);
    }

    @Override
    public DebitBankAccount BankAccountToDebitBankAccount(BankAccount bankAccount) {
        return new DebitBankAccount(bankAccount);
    }

    @Override
    public DepositBankAccount BankAccountToDepositBankAccount(BankAccount bankAccount) {
        return new DepositBankAccount(bankAccount);
    }

    @Override
    public CreditBankAccount BankAccountToCreditBankAccount(BankAccount bankAccount) {
        return new CreditBankAccount(bankAccount);
    }

    @Override
    public List<BankAccountDto> BankAccountListToBankAccountDtoList(List<BankAccount> bankAccounts) {
        if (bankAccounts == null) {
            return null;
        }

        List<BankAccountDto> list = new ArrayList<>(bankAccounts.size());
        for (BankAccount bankAccount : bankAccounts) {
            list.add(modelToDto(bankAccount));
        }

        return list;
    }
}
