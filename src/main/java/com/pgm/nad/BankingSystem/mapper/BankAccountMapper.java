package com.pgm.nad.BankingSystem.mapper;

import com.pgm.nad.BankingSystem.dto.BankAccountDto;
import com.pgm.nad.BankingSystem.model.BankAccount;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccount dtoToModel(BankAccountDto bankAccountDto);

    BankAccountDto modelToDto(BankAccount bankAccount);

    List<BankAccountDto> toListDto(List<BankAccount> bankAccounts);
}
