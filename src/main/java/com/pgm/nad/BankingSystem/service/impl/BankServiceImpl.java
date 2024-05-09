package com.pgm.nad.BankingSystem.service.impl;

import com.pgm.nad.BankingSystem.dto.BankDto;
import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.mapper.BankMapper;
import com.pgm.nad.BankingSystem.mapper.ClientMapper;
import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.model.BankAccount;
import com.pgm.nad.BankingSystem.repository.BankAccountRepository;
import com.pgm.nad.BankingSystem.repository.BankRepository;
import com.pgm.nad.BankingSystem.service.core.exceptions.BankIsNotFoundException;
import com.pgm.nad.BankingSystem.service.core.BankService;
import com.pgm.nad.BankingSystem.service.core.exceptions.NullBankException;
import com.pgm.nad.BankingSystem.service.core.exceptions.NullBankNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * A class that implements the BankService interface.
 */
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
    public BankDto findBankDtoById(long bankId) throws BankIsNotFoundException {
        BankDto bank =  bankMapper.modelToDto(bankRepository.findByBankId(bankId));
        if (bank == null) throw new BankIsNotFoundException();

        return bank;
    }

    @Override
    public List<BankDto> findAll() {
        return bankMapper.BankListToBankDtoList(bankRepository.findAll());
    }

    @Override
    public Bank findBankById(long bankId) throws BankIsNotFoundException {
        Bank bank =  bankRepository.findByBankId(bankId);
        if (bank == null) throw new BankIsNotFoundException();

        return bank;
    }

    @Override
    public List<BankDto> findAllBankForClient(ClientDto client) {
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
        List<BankDto> clientBanks = findAllBankForClient(client);
        for (BankDto bankDto : allBanks) {
            if (!clientBanks.contains(bankDto)) {
                newBanks.add(bankDto);
            }
        }
        return newBanks;
    }

    @Override
    public void save(Bank bank) throws NullBankNameException {
        System.out.println(bank.getInterestDepositRate());
        if (existsByName(bank.getName()) || existsById(bank.getBankId())) {
            long id = bank.getBankId();
            while (existsById(id)) {
                id = random.nextLong(1000, 9999);
            }
            bank.setBankId(id);
            bankRepository.save(bank);
        }
    }

    @Override
    public void update(Bank bank) {
        bankRepository.save(bank);
    }

    @Override
    public void deleteById(long bankId) throws NullBankException {
        if (bankRepository.existsById(bankId)) {
            bankAccountService.deleteAllByBank(bankRepository.findByBankId(bankId));
            bankRepository.deleteById(bankId);
        }
    }

    @Override
    public boolean existsByName(String name) throws NullBankNameException {
        if (name == null) throw new NullBankNameException("Name bank is null");

        return !bankRepository.existsByName(name);
    }

    @Override
    public boolean existsById(long bankId) {
        return bankRepository.existsById(bankId);
    }
}
