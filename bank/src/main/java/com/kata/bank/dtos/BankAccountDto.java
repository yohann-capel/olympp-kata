package com.kata.bank.dtos;

import java.util.List;

public record BankAccountDto(Long id, double balance, List<StatementDto> statements) {
}
