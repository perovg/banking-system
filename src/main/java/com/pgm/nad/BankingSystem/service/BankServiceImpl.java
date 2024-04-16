package com.pgm.nad.BankingSystem.service;

import com.pgm.nad.BankingSystem.dto.BankDto;
import com.pgm.nad.BankingSystem.mapper.BankMapper;
import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final BankMapper bankMapper;
    private final BankRepository bankRepository;
    private final BankAccountServiceImpl bankAccountService;
    public Random random = new Random();

    @Override
    public BankDto findBankDtoById(long bankId) {
        return bankMapper.modelToDto(bankRepository.findByBankId(bankId));
    }

    @Override
    public Bank findBankById(long bankId) {
        return bankRepository.findByBankId(bankId);
    }

    @Override
    public void deleteById(long bankId) {
        if (bankRepository.existsById(bankId)) {
            // Удаляем все счета, связанные с этим банком
            bankAccountService.deleteAllByBank(bankRepository.findByBankId(bankId));
            // Удаляем банк
            bankRepository.deleteById(bankId);
        }
    }

    @Override
    public boolean save(BankDto bank) {
        // Проверяем, что не существует другого банка с таким же именем, генерируем новый id и сохраняем
        if (!bankRepository.existsByName(bank.getName()) || bankRepository.existsById(bank.getBankId())) {
            long id = random.nextLong(1000, 9999);
            while (existsById(id)) {
                id = random.nextLong(1000, 9999);
            }
            bank.setBankId(id);
            bankRepository.save(bankMapper.dtoToModel(bank));
            return true;
        }
        return false;
    }

    @Override
    public void update(long bankId, double interestRate, int creditLimit) {
        BankDto bank = findBankDtoById(bankId);
        bank.setInterestRate(interestRate);
        bank.setCreditLimit(creditLimit);
    }

    @Override
    public List<BankDto> findAll() {
        return bankMapper.toListDto(bankRepository.findAll());
    }

    @Override
    public boolean existsById(long bankId) {
        return bankRepository.existsById(bankId);
    }

    @Override
    public boolean existByName(String name) {
        return bankRepository.existsByName(name);
    }

    @Override
    public BankDto findByName(String name) {
        return bankMapper.modelToDto(bankRepository.findByName(name));
    }

}
