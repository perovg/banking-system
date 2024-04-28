package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.BankDto;
import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.mapper.BankMapper;
import com.pgm.nad.BankingSystem.mapper.ClientMapper;
import com.pgm.nad.BankingSystem.model.BankAccount;
import com.pgm.nad.BankingSystem.repository.BankAccountRepository;
import com.pgm.nad.BankingSystem.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {
    private final BankMapper bankMapper;
    private final BankRepository bankRepository;
    private final BankAccountServiceImpl bankAccountService;
    private final BankAccountRepository bankAccountRepository;
    private final ClientMapper clientMapper;
    public Random random = new Random();

    @Override
    public BankDto findBankDtoById(long bankId) {
        return bankMapper.modelToDto(bankRepository.findByBankId(bankId));
    }

    @Override
    public List<BankDto> findAll() {
        return bankMapper.toListDto(bankRepository.findAll());
    }

    @Override
    public ArrayList<BankDto> findAllBankForClient(ClientDto client) {
        List<BankAccount> clientBankAccounts = bankAccountRepository.findAllByClient(clientMapper.dtoToModel(client));
        Set<BankDto> clientBanksSet = new HashSet<>();
        for (BankAccount bankAccount : clientBankAccounts) {
            clientBanksSet.add(bankMapper.modelToDto(bankAccount.getBank()));
        }
        return new ArrayList<>(clientBanksSet);
    }

    @Override
    public ArrayList<BankDto> findAllNewBanks(ClientDto client) {
        ArrayList<BankDto> newBanks = new ArrayList<>();
        List<BankDto> allBanks = findAll();
        ArrayList<BankDto> clientBanks = findAllBankForClient(client);
        for (BankDto bankDto : allBanks) {
            if (!clientBanks.contains(bankDto)) {
                newBanks.add(bankDto);
            }
        }
        return newBanks;
    }

    @Override
    public void save(BankDto bank) {
        System.out.println(bank.getInterestDepositRate());
        if (existsByName(bank.getName()) || existsById(bank.getBankId())) {
            long id = random.nextLong(1000, 9999);
            while (existsById(id)) {
                id = random.nextLong(1000, 9999);
            }
            bank.setBankId(id);
            bankRepository.save(bankMapper.dtoToModel(bank));
        }
    }

    @Override
    public void update(long bankId, String name,
                       int creditPeriod,
                       double interestCreditRate,
                       int creditLimit,
                       int depositPeriod,
                       double interestDepositRate) {
        BankDto bank = findBankDtoById(bankId);
        if (existsByName(name)) {
            bank.setName(name);
        }
        bank.setCreditPeriod(creditPeriod);
        bank.setInterestCreditRate(interestCreditRate);
        bank.setCreditLimit(creditLimit);
        bank.setDepositPeriod(depositPeriod);
        bank.setInterestDepositRate(interestDepositRate);
        bankRepository.save(bankMapper.dtoToModel(bank));
    }

    @Override
    public void deleteById(long bankId) {
        if (bankRepository.existsById(bankId)) {
            bankAccountService.deleteAllByBank(bankRepository.findByBankId(bankId));
            bankRepository.deleteById(bankId);
        }
    }

    @Override
    public boolean existsByName(String name) {
        return !bankRepository.existsByName(name);
    }

    @Override
    public boolean existsById(long bankId) {
        return bankRepository.existsById(bankId);
    }
}
