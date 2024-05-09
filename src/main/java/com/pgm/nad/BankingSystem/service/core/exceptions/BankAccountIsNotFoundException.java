package com.pgm.nad.BankingSystem.service.core.exceptions;

public class BankAccountIsNotFoundException extends Exception{
    public BankAccountIsNotFoundException() {
        super("Bank Account Not Found");
    }
}
