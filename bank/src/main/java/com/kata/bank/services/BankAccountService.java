package com.kata.bank.services;

import com.kata.bank.dtos.BankAccountDto;
import com.kata.bank.dtos.StatementCreationDto;
import com.kata.bank.dtos.StatementDto;
import com.kata.bank.entities.BankAccount;
import com.kata.bank.entities.Statement;
import com.kata.bank.exceptions.AccountNotFoundException;
import com.kata.bank.exceptions.IllegalAmountException;
import com.kata.bank.mappers.BankAccountMapper;
import com.kata.bank.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final StatementService statementService;

    public BankAccountService(BankAccountRepository bankAccountRepository, StatementService statementService) {
        this.bankAccountRepository = bankAccountRepository;
        this.statementService = statementService;
    }

    public BankAccountDto generateAccount() {
        BankAccount bankAccount = BankAccountMapper.INSTANCE.toEntity(new BankAccountDto(null, 0, List.of()));
        bankAccount = bankAccountRepository.save(bankAccount);
        return BankAccountMapper.INSTANCE.toDto(bankAccount);
    }

    public List<BankAccountDto> getAll() {
        return BankAccountMapper.INSTANCE.toDto(bankAccountRepository.findAll());
    }

    public BankAccountDto getById(long id) {
        return BankAccountMapper.INSTANCE.toDto(bankAccountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(String.format("No account with id %s", id))));
    }

    public BankAccountDto deposit(long id, Double amount) {
        if(amount == null) throw new IllegalAmountException("Amount is needed to do a deposit");
        if(amount <= 0) throw new IllegalAmountException("Can't deposit a negative amount");

        BankAccount account = bankAccountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(String.format("No account with id %s", id)));

        account.addAmount(amount);
        bankAccountRepository.save(account);

        StatementCreationDto statementCreationDto = new StatementCreationDto(BankAccountMapper.INSTANCE.toDto(account), amount, account.getBalance());
        account.addStatement(statementService.addNewStatement(statementCreationDto));

        return BankAccountMapper.INSTANCE.toDto(bankAccountRepository.save(account));
    }

    public BankAccountDto withdraw(long id, Double amount) {
        if(amount == null) throw new IllegalAmountException("Amount is needed to withdraw");
        if(amount <= 0) throw new IllegalAmountException("Can't withdraw a negative amount");

        BankAccount account = bankAccountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(String.format("No account with id %s", id)));

        account.removeAmount(amount);

        if(account.getBalance() < 0) throw new IllegalAmountException("Overdraft is not allowed");

        bankAccountRepository.save(account);

        StatementCreationDto statementCreationDto = new StatementCreationDto(BankAccountMapper.INSTANCE.toDto(account), -amount, account.getBalance());
        account.addStatement(statementService.addNewStatement(statementCreationDto));

        return BankAccountMapper.INSTANCE.toDto(bankAccountRepository.save(account));
    }
}
