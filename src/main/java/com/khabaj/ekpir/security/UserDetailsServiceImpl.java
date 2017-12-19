package com.khabaj.ekpir.security;

import com.khabaj.ekpir.persistence.domains.Account;
import com.khabaj.ekpir.persistence.repositories.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AccountRepository accountRepository;

    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account applicationUser = accountRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }
}
