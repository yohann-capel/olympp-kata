package com.kata.bank.mappers;

import com.kata.bank.dtos.BankAccountDto;
import com.kata.bank.entities.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BankAccountMapper {
    BankAccountMapper INSTANCE = Mappers.getMapper(BankAccountMapper.class);

    BankAccount toEntity(BankAccountDto bankAccountDto);
    BankAccountDto toDto(BankAccount bankAccount);
    List<BankAccountDto> toDto(List<BankAccount> bankAccounts);
}
