package com.khabaj.ekpir.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.khabaj.ekpir.persistence.domains.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    Optional<Account> findByUsername(String username);
}
