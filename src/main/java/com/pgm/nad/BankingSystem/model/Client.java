package com.pgm.nad.BankingSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    private String password;
    private String name;
    private String surname;
    private String passport;
    private String address;

    @OneToMany
    @JoinColumn(name = "clientId")
    HashSet<BankAccount> bankAccounts;

    @ManyToMany
    HashSet<Bank> banks;

    public Client(Long id, String password) {
        this.clientId = id;
        this.password = password;
        this.bankAccounts = new HashSet<>();
        this.banks = new HashSet<>();
    }
}
