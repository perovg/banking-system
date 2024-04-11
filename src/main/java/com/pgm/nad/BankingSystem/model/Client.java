package com.pgm.nad.BankingSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class Client {

    @Id
    private long clientId;

    private String name;
    private String surname;
    private String passport;
    private String address;
    @JoinTable
    @ManyToMany
    List<Bank> banks;
    public Client(Long id) {
        this.clientId = id;
    }

}
