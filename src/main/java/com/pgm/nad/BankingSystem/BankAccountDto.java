package com.pgm.nad.BankingSystem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {

    private Long id;
    private String type;
    private double balance;
    private String name;
    private String surname;
    private String passport;
    private String address;
    private boolean confirmed;

}
