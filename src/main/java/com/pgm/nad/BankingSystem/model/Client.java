package com.pgm.nad.BankingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class Client {
    @Id
    public long clientId;
    private String name;
    private String surname;
    private String passport;
    private String address;

    public Client(long clientId, String name, String surname, String passport, String address) {
        this.clientId = clientId;
        this.name = name;
        this.surname = surname;
        this.passport = passport;
        this.address = address;
    }

}
