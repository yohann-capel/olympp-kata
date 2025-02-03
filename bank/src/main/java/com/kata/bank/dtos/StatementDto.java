package com.kata.bank.dtos;

import java.time.LocalDateTime;

public record StatementDto(double amount, double balance, LocalDateTime created_at) {
}
