package com.kata.bank.mappers;

import com.kata.bank.dtos.StatementCreationDto;
import com.kata.bank.dtos.StatementDto;
import com.kata.bank.entities.Statement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StatementMapper {
    StatementMapper INSTANCE = Mappers.getMapper(StatementMapper.class);

    Statement toEntity(StatementCreationDto dto);
    StatementDto toDto(Statement statement);

    List<StatementDto> toDto(List<Statement> statements);
}
