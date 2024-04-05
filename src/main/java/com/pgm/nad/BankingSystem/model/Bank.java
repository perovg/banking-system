package com.pgm.nad.BankingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class Bank {

    @Id
    int bankId;

    String name;
    double interestRate;


    @ManyToMany
    HashSet<Client> clients;

    public Bank(int bankId, String name, double interestRate) {
        this.bankId = bankId;
        this.name = name;
        this.interestRate = interestRate;
    }
}
