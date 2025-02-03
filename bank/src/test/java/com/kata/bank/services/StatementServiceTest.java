package com.kata.bank.services;

import com.kata.bank.dtos.BankAccountDto;
import com.kata.bank.dtos.StatementCreationDto;
import com.kata.bank.dtos.StatementDto;
import com.kata.bank.entities.BankAccount;
import com.kata.bank.entities.Statement;
import com.kata.bank.exceptions.AccountNotFoundException;
import com.kata.bank.exceptions.IllegalAmountException;
import com.kata.bank.repositories.BankAccountRepository;
import com.kata.bank.repositories.StatementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StatementServiceTest {

    @Mock
    BankAccountRepository bankAccountRepository;

    @Mock
    StatementRepository statementRepository;

    @InjectMocks
    StatementService statementService;

    BankAccount bankAccount;
    Statement statement;
    List<Statement> statements;

    @BeforeEach
    void before() {
        bankAccount = new BankAccount();
        bankAccount.setId(1);
        bankAccount.setBalance(100);
        bankAccount.setStatements(new ArrayList<>());

        statement = new Statement();
        statement.setId(1);
        statement.setBalance(200);
        statement.setAmount(100);
        statement.setBankAccount(bankAccount);

        statements = List.of(statement);
    }

    @Test
    void shouldRetrieveAllStatementsWithAccountId() {
        when(bankAccountRepository.findById(bankAccount.getId())).thenReturn(Optional.of(bankAccount));
        when(statementRepository.findAllByBankAccount(bankAccount)).thenReturn(statements);

        List<StatementDto> results = statementService.getAllByAccountId(bankAccount.getId());

        assertEquals(statements.getFirst().getBalance(), results.getFirst().balance());
        assertEquals(statements.getFirst().getAmount(), results.getFirst().amount());
    }

    @Test
    void shouldThrowIfAccountDoesntExists() {
        when(bankAccountRepository.findById(bankAccount.getId())).thenReturn(Optional.empty());

        AccountNotFoundException ex = assertThrowsExactly(AccountNotFoundException.class, () -> statementService.getAllByAccountId(bankAccount.getId()));

        assertEquals("No account with id 1", ex.getMessage());


    }
}
