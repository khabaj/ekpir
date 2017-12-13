package com.khabaj.ekpir.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khabaj.ekpir.controllers.dto.RegistrationDto;
import com.khabaj.ekpir.persistence.repositories.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@Transactional
public class AccountControllerIntegrationTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    RegistrationDto registrationDto;

    @Before
    public void setUp() {
        registrationDto = new RegistrationDto();
        registrationDto.setUsername("admin");
        registrationDto.setEmail("admin@example.com");
        registrationDto.setPassword("password");
        registrationDto.setVerifyPassword("password");
    }

    @Test
    public void givenCorrectAccountData_whenRegisterNewAccount_thenReturn201Created() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/accounts")
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        assertEquals(true, accountRepository.findByUsername(registrationDto.getUsername()).isPresent());
    }

    @Test
    public void givenIncorrectAccountEmail_whenRegisterNewAccount_thenReturn400BadRequest() throws Exception {

        registrationDto.setEmail("asd@dsf@");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/accounts")
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void givenUsernameAlreadyExists_whenRegisterNewAccount_thenReturn400BadRequest() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/accounts")
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/accounts")
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
