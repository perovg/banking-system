package com.pgm.nad.BankingSystem.mapper;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.model.BankAccount;
import com.pgm.nad.BankingSystem.model.CreditBankAccount;
import com.pgm.nad.BankingSystem.model.DebitBankAccount;
import com.pgm.nad.BankingSystem.model.DepositBankAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BankAccountMapperImpl implements BankAccountMapper {

    @Override
    public BankAccountDto depositBankAccountToDto(DepositBankAccount depositBankAccount) {
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
                depositBankAccount.isCompleted());
    }

    @Override
    public BankAccountDto debitBankAccountToDto(DebitBankAccount debitBankAccount) {
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
                false);
    }

    @Override
    public BankAccountDto creditBankAccountToDto(CreditBankAccount creditBankAccount) {
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
                false);
    }

    @Override
    public DebitBankAccount BankAccountToDebitBankAccount(BankAccount bankAccount) {
        DebitBankAccount debitBankAccount = new DebitBankAccount();
        debitBankAccount.setBankAccountId(bankAccount.getBankAccountId());
        debitBankAccount.setBank(bankAccount.getBank());
        debitBankAccount.setClient(bankAccount.getClient());
        debitBankAccount.setBalance(bankAccount.getBalance());
        debitBankAccount.setType(bankAccount.getType());
        debitBankAccount.setBlocked(bankAccount.isBlocked());
        return debitBankAccount;
    }

    @Override
    public DepositBankAccount BankAccountToDepositBankAccount(BankAccount bankAccount) {
        DepositBankAccount depositBankAccount = new DepositBankAccount();
        depositBankAccount.setBankAccountId(bankAccount.getBankAccountId());
        depositBankAccount.setBank(bankAccount.getBank());
        depositBankAccount.setClient(bankAccount.getClient());
        depositBankAccount.setBalance(bankAccount.getBalance());
        depositBankAccount.setType(bankAccount.getType());
        depositBankAccount.setBlocked(bankAccount.isBlocked());

        Bank bank = bankAccount.getBank();
        depositBankAccount.setPeriod(bank.getDepositPeriod());
        depositBankAccount.setInterestRate(bank.getInterestDepositRate());
        long openTime = new Date().getTime() / 1000;
        depositBankAccount.setOpenTime(openTime);
        depositBankAccount.setPeriodEnd(openTime + bank.getDepositPeriod());
        return depositBankAccount;
    }

    @Override
    public CreditBankAccount BankAccountToCreditBankAccount(BankAccount bankAccount) {
        CreditBankAccount creditBankAccount = new CreditBankAccount();
        creditBankAccount.setBankAccountId(bankAccount.getBankAccountId());
        creditBankAccount.setBank(bankAccount.getBank());
        creditBankAccount.setClient(bankAccount.getClient());
        creditBankAccount.setBalance(bankAccount.getBalance());
        creditBankAccount.setType(bankAccount.getType());
        creditBankAccount.setBlocked(bankAccount.isBlocked());

        Bank bank = bankAccount.getBank();
        creditBankAccount.setPeriod(bank.getCreditPeriod());
        creditBankAccount.setInterestRate(bank.getInterestCreditRate());
        return creditBankAccount;
    }

    @Override
    public List<BankAccountDto> toListDto(List<BankAccount> bankAccounts) {
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
