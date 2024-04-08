package com.pgm.nad.BankingSystem.repository;

import com.pgm.nad.BankingSystem.model.Bank;
import com.pgm.nad.BankingSystem.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BankRepository extends JpaRepository<Bank, Long> {

}
