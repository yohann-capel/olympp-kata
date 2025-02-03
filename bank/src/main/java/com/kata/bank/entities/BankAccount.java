package com.kata.bank.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name = "Accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(nullable = false)
    @ColumnDefault("0")
    private double balance;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Statement> statements;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NotNull double getBalance() {
        return balance;
    }

    public void setBalance(@NotNull double balance) {
        this.balance = balance;
    }

    public void addAmount(double amount) {
        this.balance += amount;
    }

    public void removeAmount(double amount) {
        this.balance -= amount;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    public void addStatement(Statement statement) {
        this.statements.add(statement);
    }
}
