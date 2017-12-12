package com.khabaj.ekpir.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khabaj.ekpir.controllers.dto.RegistrationDto;
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

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    private AccountController accountController;
    private MockMvc mockMvc;

    @Mock
    AccountService accountService;

    @Before
    public void setUp() {
        this.accountController = new AccountController(accountService, new ModelMapper());
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void givenMathingPasswords_whenRegisterNewAccount_thenVerifyResponseStatus() throws Exception {

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setUsername("admin");
        registrationDto.setPassword("admin");
        registrationDto.setVerifyPassword("admin");

        Mockito.doNothing().when(accountService).registerNewAccount(Matchers.anyObject());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/accounts")
                        .content(asJsonString(registrationDto))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}