package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.mapper.BankAccountMapper;
import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.model.BankAccount;
import com.pgm.nad.BankingSystem.model.Client;
import com.pgm.nad.BankingSystem.model.CreditBankAccount;
import com.pgm.nad.BankingSystem.model.DebitBankAccount;
import com.pgm.nad.BankingSystem.model.DepositBankAccount;
import com.pgm.nad.BankingSystem.model.Type;
import com.pgm.nad.BankingSystem.repository.BankAccountRepository;
import com.pgm.nad.BankingSystem.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    private final ClientServiceImpl clientService;
    private final BankRepository bankRepository;
    private final Random random = new Random();

    @Override
    public BankAccountDto findBankAccountDtoById(long id) {
        recalculation(id);
        return bankAccountMapper.modelToDto(findBankAccountById(id));
    }

    private BankAccount findBankAccountById(Long id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Bank account with bankId: " + id + " not found"
                ));
    }

    @Override
    public List<BankAccountDto> findAll() {
        List<BankAccountDto> bankAccounts = bankAccountMapper.BankAccountListToBankAccountDtoList(
                bankAccountRepository.findAll()
        );
        for (BankAccountDto bankAccount : bankAccounts) {
            recalculation(bankAccount.bankAccountId());
        }
        bankAccounts = bankAccountMapper.BankAccountListToBankAccountDtoList(bankAccountRepository.findAll());
        return bankAccounts;
    }

    @Override
    public List<BankAccountDto> findAllByClient(long clientId) {
        Client client = clientService.findClientById(clientId);
        return bankAccountMapper.BankAccountListToBankAccountDtoList(bankAccountRepository.findAllByClient(client));
    }

    @Override
    public List<BankAccountDto> findAllByClientAndBank(long client, long bank) {
        List<BankAccountDto> bankAccounts =
                bankAccountMapper.
                        BankAccountListToBankAccountDtoList(bankAccountRepository.findAllByClientAndBank(
                                clientService.findClientById(client),
                                bankRepository.findByBankId(bank)));

        for (BankAccountDto bankAccount : bankAccounts) {
            recalculation(bankAccount.bankAccountId());
        }

        return bankAccountMapper.BankAccountListToBankAccountDtoList(
                bankAccountRepository.findAllByClientAndBank(
                        clientService.findClientById(client),
                        bankRepository.findByBankId(bank)));
    }

    @Override
    public void create(BankAccount bankAccount, long clientId, long bankId) {
        Bank bank = bankRepository.findByBankId(bankId);
        System.out.println(bankAccount.getBankAccountId());
        bankAccount.setBankAccountId(generateBankAccountId(bankId));
        bankAccount.setBank(bank);
        bankAccount.setClient(clientService.findClientById(clientId));
        bankAccount.setBankAccountId(generateBankAccountId(bankId));
        System.out.println(bankAccount.getBankAccountId());

        switch (bankAccount.getType()) {
            case DEBIT -> {DebitBankAccount debitBankAccount = new DebitBankAccount(bankAccount);
                System.out.println(debitBankAccount.getBankAccountId());
                    bankAccountRepository.save(new DebitBankAccount(bankAccount));}
            case DEPOSIT -> bankAccountRepository.save(new DepositBankAccount(bankAccount));
            case CREDIT -> bankAccountRepository.save(new CreditBankAccount(bankAccount));
        }
    }

    @Override
    public void deleteAllByBank(Bank bank) {
        bankAccountRepository.deleteAllByBank(bank);
    }

    private long generateBankAccountId(long bankId) {
        long id = Long.parseLong(bankId + "000000000000");
        while (bankAccountRepository.existsById(id)) {
            StringBuilder bankAccountId = new StringBuilder(Long.toString(bankId));
            for (int i = 0; i < 12; i++) {
                String symbol = Integer.toString(random.nextInt(0, 10));
                bankAccountId.append(symbol);
            }
            id = Long.parseLong(bankAccountId.toString());
        }
        return id;
    }

    public boolean topUp(long bankAccountId, double sum) {
        if (bankAccountRepository.existsById(bankAccountId) && sum > 0) {
            this.recalculation(bankAccountId);
            BankAccount bankAccount = this.findBankAccountById(bankAccountId);
            bankAccount.setBalance(bankAccount.getBalance() + sum);
            bankAccountRepository.save(bankAccount);
            return true;
        }
        return false;
    }

    @Override
    public boolean withdraw(long bankAccountId, double sum) {
        if (bankAccountRepository.existsById(bankAccountId) && sum > 0) {
            this.recalculation(bankAccountId);
            BankAccount bankAccount = this.findBankAccountById(bankAccountId);
            switch (bankAccount.getType()) {
                case DEBIT -> {
                    return withdrawFromDebitAccount((DebitBankAccount) bankAccount, sum);
                }
                case CREDIT -> {
                    return withdrawFromCreditAccount((CreditBankAccount) bankAccount, sum);
                }
                case DEPOSIT -> {
                    return withdrawFromDepositAccount((DepositBankAccount) bankAccount, sum);
                }
            }
        }
        return false;
    }

    private boolean withdrawFromDebitAccount(DebitBankAccount bankAccount, double sum) {
        if (bankAccount.getBalance() >= sum) {
            bankAccount.setBalance(bankAccount.getBalance() - sum);
            bankAccountRepository.save(bankAccount);
            return true;
        }
        return false;
    }

    private boolean withdrawFromCreditAccount(CreditBankAccount bankAccount, double sum) {
        if (bankAccount.getBalance() - sum >= -bankAccount.getBank().getCreditLimit()) {
            if (bankAccount.getBalance() - sum < 0 && bankAccount.getBalance() >= 0) {
                bankAccount.setOpenTime(new Date().getTime() / 1000);
            }
            bankAccount.setBalance(bankAccount.getBalance() - sum);
            bankAccountRepository.save(bankAccount);
            return true;
        }
        return false;
    }

    private boolean withdrawFromDepositAccount(DepositBankAccount bankAccount, double sum) {
        long realTime = new Date().getTime() / 1000;
        if (bankAccount.getPeriodEnd() <= realTime && bankAccount.getBalance() >= sum) {
            bankAccount.setBalance(bankAccount.getBalance() - sum);
            bankAccountRepository.save(bankAccount);
            return true;
        }
        return false;
    }

    @Override
    public boolean transfer(long bankAccountFromId, long bankAccountToId, double sum) {
        if (bankAccountRepository.existsById(bankAccountToId) &&
                bankAccountRepository.existsById(bankAccountFromId) &&
                sum > 0
        ) {
            if (withdraw(bankAccountFromId, sum)) {
                topUp(bankAccountToId, sum);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(long accountId) {
        BankAccount bankAccount = this.findBankAccountById(accountId);
        if (bankAccount.getBalance() == 0) {
            bankAccountRepository.deleteById(accountId);
            return true;
        }
        return false;
    }

    @Override
    public BankAccountDto blockAndUnblock(long bankAccountId) {
        BankAccount bankAccount = this.findBankAccountById(bankAccountId);

        bankAccount.setBlocked(!bankAccount.isBlocked());
        bankAccountRepository.save(bankAccount);
        return bankAccountMapper.modelToDto(bankAccount);
    }

    @Override
    public void reopenDepositAccount(long accountId) {
        BankAccount bankAccount = findBankAccountById(accountId);
        if (bankAccount.getType().equals(Type.DEPOSIT)) {
            Bank bank = bankAccount.getBank();
            DepositBankAccount depositBankAccount = (DepositBankAccount) bankAccount;

            depositBankAccount.setPeriod(bank.getDepositPeriod());
            depositBankAccount.setInterestRate(bank.getInterestDepositRate());
            long openTime = new Date().getTime() / 1000;
            depositBankAccount.setOpenTime(openTime);
            depositBankAccount.setPeriodEnd(openTime + bank.getDepositPeriod());
            depositBankAccount.setCompleted(false);

            bankAccountRepository.save(depositBankAccount);
        }
    }

    private void recalculation(long bankAccountId) {
        BankAccount bankAccount = this.findBankAccountById(bankAccountId);
        switch (bankAccount.getType()) {
            case CREDIT -> bankAccountRepository.save(recalculationCreditBankAccount((CreditBankAccount) bankAccount));
            case DEPOSIT -> bankAccountRepository.save(recalculationDepositBankAccount((DepositBankAccount) bankAccount));
        }
    }

    private BankAccount recalculationCreditBankAccount(CreditBankAccount bankAccount) {
        if (bankAccount.getBalance() >= 0) {
            bankAccount.setOpenTime(0);
        } else {
            int creditPeriods = (int) (new Date().getTime() / 1000 - bankAccount.getOpenTime()) / bankAccount.getPeriod();
            double newBalance = bankAccount.getBalance() * Math.pow(1 + bankAccount.getInterestRate() / 100, creditPeriods);

            bankAccount.setBalance(newBalance);
            bankAccount.setOpenTime(bankAccount.getOpenTime() + ((long) creditPeriods * bankAccount.getPeriod()));
        }
        return bankAccount;
    }

    private BankAccount recalculationDepositBankAccount(DepositBankAccount bankAccount) {
        if (bankAccount.getPeriodEnd() <= new Date().getTime() / 1000 && !bankAccount.isCompleted()) {
            double newBalance = bankAccount.getBalance() * (1 + bankAccount.getInterestRate() / 100);

            bankAccount.setBalance(newBalance);
            bankAccount.setCompleted(true);
        }
        return bankAccount;
    }
}
