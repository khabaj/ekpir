package com.khabaj.ekpir.controllers;

import com.khabaj.ekpir.controllers.dto.AccountDto;
import com.khabaj.ekpir.controllers.dto.RegistrationRequest;
import com.khabaj.ekpir.persistence.domains.Account;
import com.khabaj.ekpir.services.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    AccountService accountService;
    ModelMapper modelMapper;

    public AccountController(AccountService accountService, ModelMapper modelMapper) {
        this.accountService = accountService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<AccountDto> getAccount() {
        return accountService.getAccounts()
                .stream()
                .map(account -> modelMapper.map(account, AccountDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@RequestBody @Valid RegistrationRequest registrationRequest) {
        Account account = modelMapper.map(registrationRequest, Account.class);
        accountService.registerNewAccount(account);
    }
}
