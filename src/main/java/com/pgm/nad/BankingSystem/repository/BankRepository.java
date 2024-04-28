package com.pgm.nad.BankingSystem.repository;

import com.pgm.nad.BankingSystem.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Bank findByBankId(long bankId);

    boolean existsByName(String name);
}
