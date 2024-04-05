package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.mapper.BankAccountMapper;
import com.pgm.nad.BankingSystem.model.BankAccount;
import com.pgm.nad.BankingSystem.repository.BankAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    @Override
    public List<BankAccountDto> findAll() {
        return bankAccountMapper.toListDto(bankAccountRepository.findAll());
    }

    @Override
    public BankAccountDto findById(Long id) {
        return Optional.of(getById(id)).map(bankAccountMapper::modelToDto).get();
    }

    @Override
    @Transactional
    public BankAccountDto save(BankAccountDto bankAccount) {
        return bankAccountMapper.modelToDto(bankAccountRepository.save(
                bankAccountMapper.dtoToModel(bankAccount)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        var bankAccount = getById(id);
        bankAccountRepository.delete(bankAccount);
    }

    private BankAccount getById(Long id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Bank account with bankId: " + id + " not found"));
    }
}
