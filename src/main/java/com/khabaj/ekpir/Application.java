package com.khabaj.ekpir;

import com.khabaj.ekpir.persistence.domains.Account;
import com.khabaj.ekpir.persistence.repositories.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner loadData(AccountRepository accountRepository) {
		return (args) -> {
			accountRepository.save(new Account("aaaa","aaaa"));
		};
	}
}
