package com.pgm.nad.BankingSystem.mapper;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.model.BankAccount;
import com.pgm.nad.BankingSystem.model.CreditBankAccount;
import com.pgm.nad.BankingSystem.model.DebitBankAccount;
import com.pgm.nad.BankingSystem.model.DepositBankAccount;

import java.util.ArrayList;
import java.util.List;

import com.pgm.nad.BankingSystem.repository.BankRepository;
import com.pgm.nad.BankingSystem.service.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BankAccountMapperImpl implements BankAccountMapper {
    private final BankRepository bankRepository;
    private final ClientServiceImpl clientService;

    @Override
    public BankAccountDto depositBankAccountToDto(DepositBankAccount debitBankAccount) {
        if (debitBankAccount == null) {
            return null;
        }

        BankAccountDto bankAccountDto = new BankAccountDto();

        bankAccountDto.setBankAccountId(debitBankAccount.getBankAccountId());
        bankAccountDto.setType(debitBankAccount.getType());
        bankAccountDto.setBalance(debitBankAccount.getBalance());
        bankAccountDto.setBlocked(debitBankAccount.isBlocked());
        bankAccountDto.setClientId(debitBankAccount.getClient().getClientId());
        bankAccountDto.setBankId(debitBankAccount.getBank().getBankId());
        bankAccountDto.setOpenTime(debitBankAccount.getOpenTime());
        bankAccountDto.setInterestRate(debitBankAccount.getInterestRate());
        bankAccountDto.setPeriod(debitBankAccount.getPeriod());
        bankAccountDto.setPeriodEnd(debitBankAccount.getPeriodEnd());
        bankAccountDto.setCompleted(debitBankAccount.isCompleted());

        return bankAccountDto;
    }

    @Override
    public BankAccountDto debitBankAccountToDto(DebitBankAccount debitBankAccount) {
        if (debitBankAccount == null) {
            return null;
        }

        BankAccountDto bankAccountDto = new BankAccountDto();

        bankAccountDto.setBankAccountId(debitBankAccount.getBankAccountId());
        bankAccountDto.setType(debitBankAccount.getType());
        bankAccountDto.setBalance(debitBankAccount.getBalance());
        bankAccountDto.setBlocked(debitBankAccount.isBlocked());
        bankAccountDto.setClientId(debitBankAccount.getClient().getClientId());
        bankAccountDto.setBankId(debitBankAccount.getBank().getBankId());

        return bankAccountDto;
    }

    @Override
    public BankAccountDto creditBankAccountToDto(CreditBankAccount creditBankAccount) {
        if (creditBankAccount == null) {
            return null;
        }

        BankAccountDto bankAccountDto = new BankAccountDto();

        bankAccountDto.setBankAccountId(creditBankAccount.getBankAccountId());
        bankAccountDto.setType(creditBankAccount.getType());
        bankAccountDto.setBalance(creditBankAccount.getBalance());
        bankAccountDto.setBlocked(creditBankAccount.isBlocked());
        bankAccountDto.setClientId(creditBankAccount.getClient().getClientId());
        bankAccountDto.setBankId(creditBankAccount.getBank().getBankId());
        bankAccountDto.setOpenTime(creditBankAccount.getOpenTime());
        bankAccountDto.setInterestRate(creditBankAccount.getInterestRate());
        bankAccountDto.setPeriod(creditBankAccount.getPeriod());

        return bankAccountDto;
    }

    @Override
    public CreditBankAccount dtoToCreditBankAccount(BankAccountDto bankAccountDto) {
        if (bankAccountDto == null) {
            return null;
        }

        CreditBankAccount creditBankAccount = new CreditBankAccount();

        creditBankAccount.setBankAccountId(bankAccountDto.getBankAccountId());
        creditBankAccount.setBalance(bankAccountDto.getBalance());
        creditBankAccount.setType(bankAccountDto.getType());
        creditBankAccount.setBlocked(bankAccountDto.isBlocked());
        creditBankAccount.setClient(clientService.findClientById(bankAccountDto.getClientId()));
        creditBankAccount.setBank(bankRepository.findByBankId(bankAccountDto.getBankId()));
        creditBankAccount.setOpenTime(bankAccountDto.getOpenTime());
        creditBankAccount.setInterestRate(bankAccountDto.getInterestRate());
        creditBankAccount.setPeriod(bankAccountDto.getPeriod());

        return creditBankAccount;
    }

    @Override
    public DebitBankAccount dtoToDebitBankAccount(BankAccountDto bankAccountDto) {
        if (bankAccountDto == null) {
            return null;
        }

        DebitBankAccount debitBankAccount = new DebitBankAccount();

        debitBankAccount.setBankAccountId(bankAccountDto.getBankAccountId());
        debitBankAccount.setBalance(bankAccountDto.getBalance());
        debitBankAccount.setType(bankAccountDto.getType());
        debitBankAccount.setBlocked(bankAccountDto.isBlocked());
        debitBankAccount.setClient(clientService.findClientById(bankAccountDto.getClientId()));
        debitBankAccount.setBank(bankRepository.findByBankId(bankAccountDto.getBankId()));

        return debitBankAccount;
    }

    @Override
    public DepositBankAccount dtoToDepositBankAccount(BankAccountDto bankAccountDto) {
        if (bankAccountDto == null) {
            return null;
        }

        DepositBankAccount depositBankAccount = new DepositBankAccount();

        depositBankAccount.setBankAccountId(bankAccountDto.getBankAccountId());
        depositBankAccount.setBalance(bankAccountDto.getBalance());
        depositBankAccount.setType(bankAccountDto.getType());
        depositBankAccount.setBlocked(bankAccountDto.isBlocked());
        depositBankAccount.setClient(clientService.findClientById(bankAccountDto.getClientId()));
        depositBankAccount.setBank(bankRepository.findByBankId(bankAccountDto.getBankId()));
        depositBankAccount.setOpenTime(bankAccountDto.getOpenTime());
        depositBankAccount.setInterestRate(bankAccountDto.getInterestRate());
        depositBankAccount.setPeriod(bankAccountDto.getPeriod());
        depositBankAccount.setPeriodEnd(bankAccountDto.getPeriodEnd());
        depositBankAccount.setCompleted(bankAccountDto.isCompleted());

        return depositBankAccount;
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
