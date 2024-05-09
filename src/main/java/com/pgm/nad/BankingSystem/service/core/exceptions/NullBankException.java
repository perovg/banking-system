package com.pgm.nad.BankingSystem.service.core.exceptions;

public class NullBankException extends Exception {
    public NullBankException() {
        super("Bank is null!");
    }
}
