package com.pgm.nad.BankingSystem.service.core.exceptions;

public class IncorrectAccountTypeException extends Exception {
    public IncorrectAccountTypeException() {
        super("Attempt to re-opening a non-deposit account");
    }
}
