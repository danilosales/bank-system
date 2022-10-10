package dev.danilosales.bank.controller;


import dev.danilosales.bank.dto.TransactionDTO;
import dev.danilosales.bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ModelMapper modelMapper;
    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDTO createTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        var transaction = transactionService.save(transactionDTO);
        transaction.setValue(transaction.getValue().plus());
        return modelMapper.map(transaction, TransactionDTO.class);
    }
}
