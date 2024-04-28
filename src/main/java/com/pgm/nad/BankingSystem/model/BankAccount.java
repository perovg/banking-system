package com.pgm.nad.BankingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BankAccount {

    @Id
    private long bankAccountId;
    private double balance;
    private Type type;
    private boolean blocked;

    @ManyToOne
    Client client;

    @ManyToOne
    Bank bank;

}
