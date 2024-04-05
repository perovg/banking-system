package com.pgm.nad.BankingSystem.repository;

import com.pgm.nad.BankingSystem.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
