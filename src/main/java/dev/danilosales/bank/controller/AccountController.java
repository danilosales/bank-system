package dev.danilosales.bank.controller;

import dev.danilosales.bank.dto.AccountDTO;
import dev.danilosales.bank.model.Account;
import dev.danilosales.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final ModelMapper modelMapper;

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDTO createAccount(@RequestBody @Valid AccountDTO accountDTO) {
        var account = accountService.save(accountDTO);
        return modelMapper.map(account, AccountDTO.class);
    }

    @GetMapping("/{accountId}")
    public AccountDTO getAccountById(@PathVariable Long accountId) {
        var account = accountService.findById(accountId);
        return modelMapper.map(account, AccountDTO.class);
    }
}
