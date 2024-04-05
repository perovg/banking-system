package com.pgm.nad.BankingSystem.repository;

import com.pgm.nad.BankingSystem.model.BankAccount;
import com.pgm.nad.BankingSystem.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
