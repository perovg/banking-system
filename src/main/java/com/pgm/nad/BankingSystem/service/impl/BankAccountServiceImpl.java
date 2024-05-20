package com.pgm.nad.BankingSystem.service.impl;

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
import com.pgm.nad.BankingSystem.service.core.BankAccountService;
import com.pgm.nad.BankingSystem.service.core.exceptions.BankAccountServiceException;

import com.pgm.nad.BankingSystem.service.core.exceptions.ClientServiceException;
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
    public BankAccountDto findBankAccountDtoById(long id) throws BankAccountServiceException {
        if (!bankAccountRepository.existsById(id)) throw new BankAccountServiceException("Bank account with this id not found!");

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
    public List<BankAccountDto> findAllByClient(long clientId) throws BankAccountServiceException, ClientServiceException {
        Client client = clientService.findClientById(clientId);
        if (client == null) throw new BankAccountServiceException("Client is not found!");

        return bankAccountMapper.BankAccountListToBankAccountDtoList(bankAccountRepository.findAllByClient(client));
    }

    @Override
    public List<BankAccountDto> findAllByClientAndBank(long clientId, long bankId)
            throws BankAccountServiceException, ClientServiceException {
        if (!clientService.existById(clientId)) throw new BankAccountServiceException("Client is not found!");
        if (!bankRepository.existsById(bankId)) throw new BankAccountServiceException("Bank is not found exception!");

        List<BankAccountDto> bankAccounts =
                bankAccountMapper.
                        BankAccountListToBankAccountDtoList(bankAccountRepository.findAllByClientAndBank(
                                clientService.findClientById(clientId),
                                bankRepository.findByBankId(bankId)));

        for (BankAccountDto bankAccount : bankAccounts) {
            recalculation(bankAccount.bankAccountId());
        }

        return bankAccountMapper.BankAccountListToBankAccountDtoList(
                bankAccountRepository.findAllByClientAndBank(
                        clientService.findClientById(clientId),
                        bankRepository.findByBankId(bankId)));
    }

    @Override
    public void create(BankAccount bankAccount, long clientId, long bankId)
            throws BankAccountServiceException, ClientServiceException {
        if (!bankRepository.existsById(bankId)) throw new BankAccountServiceException("Bank with this ID is not in database!");
        if (!clientService.existById(clientId)) throw new BankAccountServiceException("Client with this ID is not in database!");

        Bank bank = bankRepository.findByBankId(bankId);
        bankAccount.setBankAccountId(generateBankAccountId(bankId));
        bankAccount.setBank(bank);
        bankAccount.setClient(clientService.findClientById(clientId));
        bankAccount.setBankAccountId(generateBankAccountId(bankId));

        switch (bankAccount.getType()) {
            case DEBIT -> bankAccountRepository.save(bankAccountMapper.BankAccountToDebitBankAccount(bankAccount));
            case DEPOSIT -> bankAccountRepository.save(bankAccountMapper.BankAccountToDepositBankAccount(bankAccount));
            case CREDIT -> bankAccountRepository.save(bankAccountMapper.BankAccountToCreditBankAccount(bankAccount));
        }
    }

    @Override
    public void deleteAllByBank(Bank bank) throws BankAccountServiceException {
        if (bank == null) throw new BankAccountServiceException("Bank is null!");
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

    @Override
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
    public boolean deleteById(long accountId) throws BankAccountServiceException {
        if (!this.bankAccountRepository.existsById(accountId)) throw new BankAccountServiceException("Bank account is not find!");
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
    public void reopenDepositAccount(long accountId) throws BankAccountServiceException {
        BankAccount bankAccount = findBankAccountById(accountId);
        if (bankAccount == null) throw new BankAccountServiceException("Bank account is null!");
        if (bankAccount.getType().equals(Type.DEPOSIT)) {
            DepositBankAccount depositBankAccount = DepositBankAccount.reopenDepositBankAccount(bankAccount);

            bankAccountRepository.save(depositBankAccount);
        } else {
            throw new BankAccountServiceException("Incorrect bank account type");
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
