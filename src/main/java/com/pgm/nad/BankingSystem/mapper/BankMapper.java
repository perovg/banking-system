package com.pgm.nad.BankingSystem.mapper;

import com.pgm.nad.BankingSystem.dto.BankDto;
import com.pgm.nad.BankingSystem.model.Bank;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankMapper {
    Bank dtoToModel(BankDto bankDto);

    BankDto modelToDto(Bank bank);

    List<BankDto> toListDto(List<Bank> banks);
}
