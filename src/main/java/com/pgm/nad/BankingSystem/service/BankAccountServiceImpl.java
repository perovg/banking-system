package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.dto.ClientDto;
import com.pgm.nad.BankingSystem.mapper.BankAccountMapper;
import com.pgm.nad.BankingSystem.mapper.ClientMapper;
import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.model.BankAccount;
import com.pgm.nad.BankingSystem.model.Client;
import com.pgm.nad.BankingSystem.repository.BankAccountRepository;
import com.pgm.nad.BankingSystem.repository.BankRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    private final ClientMapper clientMapper;
    private final ClientServiceImpl clientService;
    private final BankRepository bankRepository;

    @Override
    public ArrayList<Bank> findAllBankForClient(ClientDto client) {
        List<BankAccount> clientBankAccounts = bankAccountRepository.findAllByClient(clientMapper.dtoToModel(client));
        Set<Bank> clientBanksSet = new HashSet<>();
        for (BankAccount bankAccount : clientBankAccounts) {
            clientBanksSet.add(bankAccount.getBank());
        }
        return new ArrayList<>(clientBanksSet);
    }

    @Override
    public List<BankAccountDto> findAll() {
        return bankAccountMapper.toListDto(bankAccountRepository.findAll());
    }

    @Override
    public List<BankAccountDto> findAllByClientAndBank(Client client, Bank bank) {
        return bankAccountMapper.toListDto(bankAccountRepository.findAllByClientAndBank(client, bank));
    }

    @Override
    public BankAccountDto findById(Long id) {
        return Optional.of(getById(id)).map(bankAccountMapper::modelToDto).get();
    }

    @Override
    public void save(BankAccountDto bankAccount, long clientId, long bankId) {
        BankAccount newAccount = new BankAccount();
        newAccount.setBank(bankRepository.findByBankId(bankId));
        newAccount.setClient(clientService.findClientById(clientId));
        newAccount.setType(bankAccount.getType());
        newAccount.setBalance(bankAccount.getBalance());
        bankAccountRepository.save(
                bankAccountMapper.dtoToModel(bankAccount));
    }

    @Override
    public BankAccountDto save(BankAccountDto bankAccount) {
        return bankAccountMapper.modelToDto(bankAccountRepository.save(
                bankAccountMapper.dtoToModel(bankAccount)));
    }

    @Override
    public void deleteById(Long id) {
        var bankAccount = getById(id);
        bankAccountRepository.delete(bankAccount);
    }

    @Override
    public void deleteAllByBank(Bank bank) {
        bankAccountRepository.deleteAllByBank(bank);
    }

    private BankAccount getById(Long id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Bank account with bankId: " + id + " not found"));
    }
}
