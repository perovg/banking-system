package com.pgm.nad.BankingSystem.service.core.exceptions;

public class ClientIsNotFoundException extends Exception {
    public ClientIsNotFoundException() {
        super("Client is not found");
    }
}
