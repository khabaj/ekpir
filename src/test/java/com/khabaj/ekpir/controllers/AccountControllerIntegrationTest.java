package com.khabaj.ekpir.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khabaj.ekpir.controllers.dto.RegistrationRequest;
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

    RegistrationRequest registrationRequest;

    @Before
    public void setUp() {
        registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("admin");
        registrationRequest.setEmail("admin@example.com");
        registrationRequest.setPassword("password");
        registrationRequest.setVerifyPassword("password");
    }

    @Test
    public void givenCorrectAccountData_whenRegisterNewAccount_thenReturn201Created() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/accounts")
                        .content(objectMapper.writeValueAsString(registrationRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        assertEquals(true, accountRepository.findByUsername(registrationRequest.getUsername()).isPresent());
    }

    @Test
    public void givenIncorrectAccountEmail_whenRegisterNewAccount_thenReturn400BadRequest() throws Exception {

        registrationRequest.setEmail("asd@dsf@");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/accounts")
                        .content(objectMapper.writeValueAsString(registrationRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void givenUsernameAlreadyExists_whenRegisterNewAccount_thenReturn400BadRequest() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/accounts")
                        .content(objectMapper.writeValueAsString(registrationRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/accounts")
                        .content(objectMapper.writeValueAsString(registrationRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
