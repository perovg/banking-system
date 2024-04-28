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
import java.util.Optional;
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
    public BankAccountDto findById(long id) {
        recount(id);
        return Optional.of(getById(id)).map(bankAccountMapper::modelToDto).get();
    }

    private BankAccount getById(Long id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Bank account with bankId: " + id + " not found"));
    }

    @Override
    public List<BankAccountDto> findAll() {
        List<BankAccountDto> bankAccounts = bankAccountMapper.toListDto(bankAccountRepository.findAll());
        for (BankAccountDto bankAccount : bankAccounts) {
            recount(bankAccount.getBankAccountId());
        }
        bankAccounts = bankAccountMapper.toListDto(bankAccountRepository.findAll());
        return bankAccounts;
    }

    @Override
    public List<BankAccountDto> findAllByClient(long clientId) {
        Client client = clientService.findClientById(clientId);
        return bankAccountMapper.toListDto(bankAccountRepository.findAllByClient(client));
    }

    @Override
    public List<BankAccountDto> findAllByClientAndBank(long client, long bank) {
        List<BankAccountDto> bankAccounts =
                bankAccountMapper.
                        toListDto(bankAccountRepository.findAllByClientAndBank(
                                clientService.findClientById(client),
                                bankRepository.findByBankId(bank)));

        for (BankAccountDto bankAccount : bankAccounts) {
            recount(bankAccount.getBankAccountId());
        }

        return bankAccountMapper.toListDto(
                bankAccountRepository.findAllByClientAndBank(
                        clientService.findClientById(client),
                        bankRepository.findByBankId(bank)));
    }

    @Override
    public void create(BankAccountDto bankAccount) {
        if (bankAccount.getType().equals(Type.DEBIT)) {
            DebitBankAccount newAccount = bankAccountMapper.dtoToDebitBankAccount(bankAccount);

            newAccount.setBankAccountId(generateBankAccountId(bankAccount.getBankId()));
            newAccount.setBank(bankRepository.findByBankId(bankAccount.getBankId()));
            newAccount.setClient(clientService.findClientById(bankAccount.getClientId()));

            bankAccountRepository.save(newAccount);

        } else if (bankAccount.getType().equals(Type.CREDIT)) {
            Bank bank = bankRepository.findByBankId(bankAccount.getBankId());
            CreditBankAccount newAccount = bankAccountMapper.dtoToCreditBankAccount(bankAccount);

            newAccount.setBank(bank);
            newAccount.setBankAccountId(generateBankAccountId(bankAccount.getBankId()));
            newAccount.setClient(clientService.findClientById(bankAccount.getClientId()));
            newAccount.setPeriod(bank.getCreditPeriod());
            newAccount.setInterestRate(bank.getInterestCreditRate());

            bankAccountRepository.save(newAccount);

        } else if (bankAccount.getType().equals(Type.DEPOSIT)) {
            Bank bank = bankRepository.findByBankId(bankAccount.getBankId());
            DepositBankAccount newAccount = bankAccountMapper.dtoToDepositBankAccount(bankAccount);

            newAccount.setBank(bank);
            newAccount.setBankAccountId(generateBankAccountId(bankAccount.getBankId()));
            newAccount.setClient(clientService.findClientById(bankAccount.getClientId()));
            newAccount.setPeriod(bank.getDepositPeriod());
            newAccount.setInterestRate(bank.getInterestDepositRate());
            long openTime = new Date().getTime() / 1000;
            newAccount.setOpenTime(openTime);
            newAccount.setPeriodEnd(openTime + bank.getDepositPeriod());

            bankAccountRepository.save(newAccount);
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
            this.recount(bankAccountId);
            BankAccount bankAccount = this.getById(bankAccountId);
            bankAccount.setBalance(bankAccount.getBalance() + sum);
            bankAccountRepository.save(bankAccount);
            return true;
        }
        return false;
    }

    @Override
    public boolean withdraw(long bankAccountId, double sum) {
        if (bankAccountRepository.existsById(bankAccountId) && sum > 0) {
            this.recount(bankAccountId);
            BankAccount bankAccount = this.getById(bankAccountId);
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
        if (bankAccountRepository.existsById(bankAccountToId) && bankAccountRepository.existsById(bankAccountFromId) && sum > 0) {
            if (withdraw(bankAccountFromId, sum)) {
                topUp(bankAccountToId, sum);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(long accountId) {
        BankAccount bankAccount = this.getById(accountId);
        if (bankAccount.getBalance() == 0) {
            bankAccountRepository.deleteById(accountId);
            return true;
        }
        return false;
    }

    @Override
    public BankAccountDto blockAndUnblock(long bankAccountId, boolean isBlocked) {
        BankAccount bankAccount = this.getById(bankAccountId);

        bankAccount.setBlocked(!isBlocked);
        bankAccountRepository.save(bankAccount);
        return bankAccountMapper.modelToDto(bankAccount);
    }

    @Override
    public void reopenDepositAccount(long accountId) {
        BankAccount bankAccount = getById(accountId);
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

    private void recount(long bankAccountId) {
        BankAccount bankAccount = this.getById(bankAccountId);
        switch (bankAccount.getType()) {
            case CREDIT -> bankAccountRepository.save(recountCreditBankAccount((CreditBankAccount) bankAccount));
            case DEPOSIT -> bankAccountRepository.save(recountDepositBankAccount((DepositBankAccount) bankAccount));
        }
    }

    private BankAccount recountCreditBankAccount(CreditBankAccount bankAccount) {
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

    private BankAccount recountDepositBankAccount(DepositBankAccount bankAccount) {
        if (bankAccount.getPeriodEnd() <= new Date().getTime() / 1000 && !bankAccount.isCompleted()) {
            double newBalance = bankAccount.getBalance() * (1 + bankAccount.getInterestRate() / 100);

            bankAccount.setBalance(newBalance);
            bankAccount.setCompleted(true);
        }
        return bankAccount;
    }
}
