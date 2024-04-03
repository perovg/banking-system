package com.pgm.nad.BankingSystem;

import java.util.List;

public interface BankAccountService {
    List<BankAccountDto> findAll();
    BankAccountDto findById(Long id);
    BankAccountDto save(BankAccountDto bankAccount);
    void deleteById(Long id);
}
