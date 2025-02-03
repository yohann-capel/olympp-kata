package com.kata.bank.services;

import com.kata.bank.dtos.StatementCreationDto;
import com.kata.bank.dtos.StatementDto;
import com.kata.bank.entities.BankAccount;
import com.kata.bank.entities.Statement;
import com.kata.bank.exceptions.AccountNotFoundException;
import com.kata.bank.mappers.StatementMapper;
import com.kata.bank.repositories.BankAccountRepository;
import com.kata.bank.repositories.StatementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatementService {
    private final StatementRepository statementRepository;
    private final BankAccountRepository bankAccountRepository;

    public StatementService(StatementRepository statementRepository, BankAccountRepository bankAccountRepository) {
        this.statementRepository = statementRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public Statement addNewStatement(StatementCreationDto statementCreationDto) {
        Statement newStatement = StatementMapper.INSTANCE.toEntity(statementCreationDto);
        return statementRepository.save(newStatement);
    }

    public List<StatementDto> getAllByAccountId(long accountId) {
        BankAccount bankAccount = bankAccountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format("No account with id %s", accountId)));
        List<Statement> statements = statementRepository.findAllByBankAccount(bankAccount);
        return StatementMapper.INSTANCE.toDto(statements);
    }
}
