package com.kata.bank.repositories;

import com.kata.bank.entities.BankAccount;
import com.kata.bank.entities.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {
    List<Statement> findAllByBankAccount(BankAccount bankAccount);
}
