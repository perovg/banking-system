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

}
