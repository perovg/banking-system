package com.pgm.nad.BankingSystem;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;
    private String type;
    private String name;
    private String surname;
    private String passport;
    private String address;
    private boolean confirmed;

}
