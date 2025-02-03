package com.kata.bank.dtos;

import java.time.LocalDateTime;

public record StatementCreationDto(BankAccountDto bankAccount, double amount, double balance) {
}
