package com.pgm.nad.BankingSystem.service.core.exceptions;

public class BankIsNotFoundException extends Exception {
    public BankIsNotFoundException() {
        super("Bank is not found");
    }
}
