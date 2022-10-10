package dev.danilosales.bank.service;

import dev.danilosales.bank.dto.AccountDTO;
import dev.danilosales.bank.exceptions.EntityNotFoundException;
import dev.danilosales.bank.model.Account;
import dev.danilosales.bank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;



    @Transactional
    public Account save(AccountDTO accountDTO) {
        var account = modelMapper.map(accountDTO, Account.class);
        accountRepository.save(account);
        return account;
    }

    public Account findById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(Account.class, "id", accountId.toString()));
    }
}
