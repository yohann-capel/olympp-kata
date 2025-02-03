package com.kata.bank.controllers;

import com.kata.bank.dtos.StatementDto;
import com.kata.bank.services.StatementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statement")
public class StatementController {
    private final StatementService statementService;

    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<List<StatementDto>> getAllStatements(@PathVariable String accountId) {
        return ResponseEntity.ok(statementService.getAllByAccountId(Long.parseLong(accountId)));
    }
}
