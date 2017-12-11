package com.khabaj.ekpir.rest;

import com.khabaj.ekpir.rest.dto.AccountDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/users")
public class AccountController {

    @PostMapping
    public void registerAccount(@Valid AccountDto accountDto) {

    }
}
