package dev.danilosales.bank.config;

import dev.danilosales.bank.dto.AccountDTO;
import dev.danilosales.bank.dto.TransactionDTO;
import dev.danilosales.bank.enums.OperationType;
import dev.danilosales.bank.model.Account;
import dev.danilosales.bank.model.Transaction;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {


    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        var typeAccount = modelMapper.createTypeMap(AccountDTO.class, Account.class);
        typeAccount.addMappings(mapper -> mapper.skip(Account::setId));

        Converter<Integer, OperationType> operationTypeConverter = ctx -> ctx.getSource() == null ? null : OperationType.getByCode(ctx.getSource());
        Converter<OperationType, Integer> operationTypeDTOConverter = ctx -> ctx.getSource() == null ? null : ctx.getSource().getCode();

        var typeTransaction = modelMapper.createTypeMap(TransactionDTO.class, Transaction.class);
        typeTransaction.addMappings(mapper -> {
            mapper.skip(Transaction::setId);
            mapper.using(operationTypeConverter).map(TransactionDTO::getOperationType, Transaction::setOperationType);
        });

        var typeDTOTransaction = modelMapper.createTypeMap(Transaction.class, TransactionDTO.class);
        typeDTOTransaction.addMappings(mapper -> mapper.using(operationTypeDTOConverter).map(Transaction::getOperationType, TransactionDTO::setOperationType));


        return modelMapper;
    }
}
