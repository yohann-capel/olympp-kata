package com.kata.bank.controllers;

import com.kata.bank.dtos.AmountDataDto;
import com.kata.bank.dtos.BankAccountDto;
import com.kata.bank.mappers.BankAccountMapper;
import com.kata.bank.services.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bank")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    public BankAccountController (BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
    @GetMapping
    public ResponseEntity<List<BankAccountDto>> getAllAccounts() {
        return ResponseEntity.ok(bankAccountService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDto> getBankAccount(@PathVariable String id) {
        return ResponseEntity.ok(bankAccountService.getById(Long.parseLong(id)));
    }

    @PostMapping("/init")
    public ResponseEntity<BankAccountDto> initNewAccount() {
        return ResponseEntity.ok(bankAccountService.generateAccount());
    }

    @PutMapping("/deposit")
    public ResponseEntity<BankAccountDto> doDeposit(@RequestBody AmountDataDto data) {
        return ResponseEntity.ok(bankAccountService.deposit(data.accountId(), data.amount()));
    }

    @PutMapping("/withdraw")
    public ResponseEntity<BankAccountDto> doWithdraw(@RequestBody AmountDataDto data) {
        return ResponseEntity.ok(bankAccountService.withdraw(data.accountId(), data.amount()));
    }
}
