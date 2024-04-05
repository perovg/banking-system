package com.pgm.nad.BankingSystem.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankAccountId;
    private double balance;
    private String type;
    private boolean confirmed;

}
