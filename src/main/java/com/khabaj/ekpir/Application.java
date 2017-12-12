package com.khabaj.ekpir;

import com.khabaj.ekpir.persistence.domains.Account;
import com.khabaj.ekpir.services.AccountService;
import org.modelmapper.ModelMapper;
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
    public CommandLineRunner loadData(AccountService accountService) {
        return (args) -> accountService.registerNewAccount(new Account("admin", "admin123"));
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
