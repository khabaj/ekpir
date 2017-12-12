package com.khabaj.ekpir.services;

import com.khabaj.ekpir.exceptions.UsernameExistsException;
import com.khabaj.ekpir.persistence.domains.Account;
import com.khabaj.ekpir.persistence.repositories.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountService {

    AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public void registerNewAccount(Account account) throws UsernameExistsException {

        if (usernameExists(account.getUsername())) {
            throw new UsernameExistsException("Account with username: " + account.getUsername() + " already exists.");
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
    }

    private boolean usernameExists(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }
}
