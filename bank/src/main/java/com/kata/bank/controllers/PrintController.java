package com.kata.bank.controllers;

import com.kata.bank.services.PrintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/print")
public class PrintController {
    private final PrintService printService;

    public PrintController(PrintService printService) {
        this.printService = printService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<String> print(@PathVariable String accountId) {
        return ResponseEntity.ok(printService.print(Long.parseLong(accountId)));
    }
}