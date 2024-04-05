package com.pgm.nad.BankingSystem.controller;

import com.pgm.nad.BankingSystem.service.BankAccountService;
import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class BankAccountsController {
    private final BankAccountService bankAccountService;

    @GetMapping("/bankAccounts")
    public List<BankAccountDto> allBankAccounts() {
        return bankAccountService.findAll();
    }

    @GetMapping("/bankAccounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BankAccountDto> getBankAccount(@PathVariable Long id) {
        return ResponseEntity.ok().body(bankAccountService.findById(id));
    }

    @PostMapping("/bankAccount")
    public ResponseEntity<BankAccountDto> createBankAccount(@RequestBody BankAccountDto bankAccount) throws URISyntaxException {
        BankAccountDto result = bankAccountService.save(bankAccount);
        return ResponseEntity.created(new URI("/bankAccounts/" + result.bankAccountId()))
                .body(result);
    }

    @PutMapping("/bankAccounts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BankAccountDto> updateBankAccount(@PathVariable Long id, @RequestBody BankAccountDto bankAccount) {
        return ResponseEntity.ok().body(bankAccountService.save(bankAccount));
    }

    @DeleteMapping("/bankAccount/{id}")
    public ResponseEntity<?> deleteBankAccount(@PathVariable Long id) {
        bankAccountService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
