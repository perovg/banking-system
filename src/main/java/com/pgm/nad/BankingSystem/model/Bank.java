package com.pgm.nad.BankingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

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

}
