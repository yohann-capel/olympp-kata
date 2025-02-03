package com.kata.bank.services;

import com.kata.bank.dtos.BankAccountDto;
import com.kata.bank.dtos.StatementCreationDto;
import com.kata.bank.entities.BankAccount;
import com.kata.bank.entities.Statement;
import com.kata.bank.exceptions.AccountNotFoundException;
import com.kata.bank.exceptions.IllegalAmountException;
import com.kata.bank.repositories.BankAccountRepository;
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
public class BankAccountServiceTest {
    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private StatementService statementService;

    @InjectMocks
    BankAccountService bankAccountService;

    BankAccount account;

    @BeforeEach
    void initBankAccount() {
        account = new BankAccount();
        account.setId(1L);
        account.setBalance(0d);
        account.setStatements(new ArrayList<>());
    }

    @Test
    void shouldGenerateAnAccountWithNoMoney() {
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(account);

        BankAccountDto test = bankAccountService.generateAccount();

        assertNotNull(test);
        assertEquals(account.getId(), test.id());
        assertEquals(account.getBalance(), test.balance());
    }

    @Test
    void depositShouldThrowIfAmountIsNull() {
        IllegalAmountException ex = assertThrowsExactly(
                IllegalAmountException.class,
                () -> bankAccountService.deposit(account.getId(), null));

        assertEquals("Amount is needed to do a deposit", ex.getMessage());
    }

    @Test
    void depositShouldThrowIfAmountIsNegative() {
        IllegalAmountException ex = assertThrowsExactly(
                IllegalAmountException.class,
                () -> bankAccountService.deposit(account.getId(), -1d));

        assertEquals("Can't deposit a negative amount", ex.getMessage());
    }

    @Test
    void depositShouldNotFindAccount() {
        when(bankAccountRepository.findById(account.getId())).thenReturn(Optional.empty());
        AccountNotFoundException ex = assertThrowsExactly(AccountNotFoundException.class, () -> bankAccountService.deposit(account.getId(), 1d));

        assertEquals("No account with id 1", ex.getMessage());
    }

    @Test
    void depositShouldAddAmountToAccount() {
        when(bankAccountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(account);
        when(statementService.addNewStatement(any(StatementCreationDto.class))).thenReturn(null);

        BankAccountDto result = bankAccountService.deposit(account.getId(), 100d);

        assertNotNull(result);
        assertEquals(account.getId(), result.id());
        assertEquals(100d, result.balance());
    }

    @Test
    void withdrawalShouldThrowIfAmountIsNull() {
        IllegalAmountException ex = assertThrowsExactly(
                IllegalAmountException.class,
                () -> bankAccountService.withdraw(account.getId(), null));

        assertEquals("Amount is needed to withdraw", ex.getMessage());
    }

    @Test
    void withdrawalShouldThrowIfAmountIsNegative() {
        IllegalAmountException ex = assertThrowsExactly(
                IllegalAmountException.class,
                () -> bankAccountService.withdraw(account.getId(), -1d));

        assertEquals("Can't withdraw a negative amount", ex.getMessage());
    }

    @Test
    void withdrawalShouldNotFindAccount() {
        account.setBalance(10d);
        when(bankAccountRepository.findById(account.getId())).thenReturn(Optional.empty());
        AccountNotFoundException ex = assertThrowsExactly(AccountNotFoundException.class, () -> bankAccountService.withdraw(account.getId(), 1d));

        assertEquals("No account with id 1", ex.getMessage());
    }

    @Test
    void withdrawalShouldNotLetUserOverDraft() {
        when(bankAccountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(account);

        IllegalAmountException ex = assertThrowsExactly(
                IllegalAmountException.class,
                () -> bankAccountService.withdraw(account.getId(), 1d));

        assertEquals("Overdraft is not allowed", ex.getMessage());
    }

    @Test
    void withdrawalShouldRemoveAmountToAccount() {
        account.setBalance(150d);
        when(bankAccountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(account);
        when(statementService.addNewStatement(any(StatementCreationDto.class))).thenReturn(null);

        BankAccountDto result = bankAccountService.withdraw(account.getId(), 100d);

        assertNotNull(result);
        assertEquals(account.getId(), result.id());
        assertEquals(50d, result.balance());
    }

    @Test
    void depositShouldAddAStatementLine() {
        double amount = 100d;
        account.setBalance(150d);

        Statement statement = new Statement();
        statement.setId(1L);
        statement.setAmount(amount);
        statement.setBalance(account.getBalance());
        statement.setBankAccount(account);

        when(bankAccountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(account);
        when(statementService.addNewStatement(any(StatementCreationDto.class))).thenReturn(statement);

        BankAccountDto result = bankAccountService.deposit(account.getId(), 100d);

        assertEquals(1, account.getStatements().size());
        verify(bankAccountRepository, times(2)).save(any(BankAccount.class));
    }

    @Test
    void withdrawalShouldAddAStatementLine() {
        double amount = 100d;
        account.setBalance(150d);

        Statement statement = new Statement();
        statement.setId(1L);
        statement.setAmount(amount);
        statement.setBalance(account.getBalance());
        statement.setBankAccount(account);

        when(bankAccountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(account);
        when(statementService.addNewStatement(any(StatementCreationDto.class))).thenReturn(statement);

        BankAccountDto result = bankAccountService.withdraw(account.getId(), 100d);

        assertEquals(1, account.getStatements().size());
        verify(bankAccountRepository, times(2)).save(any(BankAccount.class));
    }

    @Test
    void shouldReturnAccountBasedOnId() {
        when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.of(account));

        BankAccountDto result = bankAccountService.getById(account.getId());

        assertEquals(account.getId(), result.id());
        assertEquals(account.getBalance(), result.balance());
    }

    @Test
    void shouldThrowIfAccountDoesntExists() {
        when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        AccountNotFoundException ex = assertThrowsExactly(AccountNotFoundException.class, () -> bankAccountService.getById(account.getId()));

        assertEquals("No account with id 1", ex.getMessage());
    }

    @Test
    void shouldReturnListOfAccounts() {
        when(bankAccountRepository.findAll()).thenReturn(List.of(account));

        List<BankAccountDto> dtos = bankAccountService.getAll();

        assertEquals(1, dtos.size());
        assertEquals(account.getId(), dtos.getFirst().id());
        assertEquals(account.getBalance(), dtos.getFirst().balance());
    }
}
