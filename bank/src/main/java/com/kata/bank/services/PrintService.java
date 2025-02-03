package com.kata.bank.services;

import com.kata.bank.dtos.BankAccountDto;
import com.kata.bank.dtos.StatementDto;
import com.kata.bank.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class PrintService {

    private final BankAccountService service;

    public PrintService(BankAccountService service) {
        this.service = service;
    }

    public String print(long id) {
        BankAccountDto dto = service.getById(id);
        StringBuilder result = new StringBuilder("DATE                 AMOUNT     BALANCE\n");
        result.append("------------------------------------------------\n");

        for(StatementDto statementDto : dto.statements()) {
            result.append(formattedStatement(statementDto));
        }

        return result.toString();
    }

    public String formattedStatement(StatementDto statement) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("%-20s %-10.2f %-10.2f\n", statement.created_at().format(formatter), statement.amount(), statement.balance());
    }
}
