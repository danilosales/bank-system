package dev.danilosales.bank.service;

import dev.danilosales.bank.dto.TransactionDTO;
import dev.danilosales.bank.model.Transaction;
import dev.danilosales.bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    public Transaction save(TransactionDTO transactionDTO) {
        var transaction = modelMapper.map(transactionDTO, Transaction.class);

        if (!transaction.getOperationType().isPositive()) {
            transaction.setValue(transaction.getValue().negate());
        }

        transactionRepository.save(transaction);

        return transaction;

    }
}
