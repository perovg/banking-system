package com.pgm.nad.BankingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class Bank {

    @Id
    long bankId;

    String name;
    double interestRate;
    int creditLimit;

    @ManyToMany
    List<Client> clients;
}
