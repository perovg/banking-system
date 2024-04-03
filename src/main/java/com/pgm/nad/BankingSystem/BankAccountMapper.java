package com.pgm.nad.BankingSystem;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccount dtoToModel(BankAccountDto bankAccountDto);

    BankAccountDto modelToDto(BankAccount bankAccount);

    List<BankAccountDto> toListDto(List<BankAccount> bankAccounts);
}
