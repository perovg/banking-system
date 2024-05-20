package com.pgm.nad.BankingSystem.repository;

import com.pgm.nad.BankingSystem.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {}
