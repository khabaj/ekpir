package com.khabaj.ekpir.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khabaj.ekpir.controllers.dto.RegistrationDto;
import com.khabaj.ekpir.exceptions.UsernameExistsException;
import com.khabaj.ekpir.services.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    @Mock
    AccountService accountService;
    private AccountController accountController;
    private MockMvc mockMvc;
    private RegistrationDto registrationDto;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.accountController = new AccountController(accountService, new ModelMapper());
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
        this.objectMapper = new ObjectMapper();

        registrationDto = new RegistrationDto();
        registrationDto.setUsername("admin");
        registrationDto.setEmail("admin@example.com");
        registrationDto.setPassword("password");
        registrationDto.setVerifyPassword("password");
    }

    @Test
    public void givenCorrectAccountData_whenRegisterNewAccount_thenOk() throws Exception {

        Mockito.doNothing().when(accountService).registerNewAccount(Matchers.anyObject());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/accounts")
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void givenNotMathingPasswords_whenRegisterNewAccount_thenReturn400BadRequest() throws Exception {

        registrationDto.setVerifyPassword("password2222");

        Mockito.doNothing().when(accountService).registerNewAccount(Matchers.anyObject());

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

        Mockito.doThrow(UsernameExistsException.class).when(accountService).registerNewAccount(Matchers.anyObject());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/accounts")
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}