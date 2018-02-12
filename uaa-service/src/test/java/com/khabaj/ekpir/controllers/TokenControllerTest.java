package com.khabaj.ekpir.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khabaj.ekpir.controllers.dto.AuthenticationRequest;
import com.khabaj.ekpir.security.SecurityConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;

@RunWith(MockitoJUnitRunner.class)
public class TokenControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;
    private TokenController tokenController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.tokenController = new TokenController(authenticationManager);
        this.mockMvc = MockMvcBuilders.standaloneSetup(tokenController).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void givenCorrectCredencials_whenAuthenticating_thenReturnToken() throws Exception {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("admin");
        authenticationRequest.setPassword("admin");

        Mockito.when(authenticationManager.authenticate(Matchers.any())).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/auth/token")
                        .content(objectMapper.writeValueAsString(authenticationRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String header = mvcResult.getResponse().getHeader(HttpHeaders.AUTHORIZATION);

        Assert.assertTrue(!StringUtils.isEmpty(header));
        Assert.assertTrue(header.startsWith(SecurityConstants.TOKEN_PREFIX));
    }

    @Test
    public void givenCorrectCredencialsWithSetCookieParam_whenAuthenticating_thenAddTokenToCookie() throws Exception {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("admin");
        authenticationRequest.setPassword("admin");

        Mockito.when(authenticationManager.authenticate(Matchers.any())).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/auth/token")
                        .content(objectMapper.writeValueAsString(authenticationRequest))
                        .param("setCookie", "true")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String authHeader = mvcResult.getResponse().getHeader(HttpHeaders.AUTHORIZATION);
        Cookie authCookie = mvcResult.getResponse().getCookie(HttpHeaders.AUTHORIZATION);

        Assert.assertTrue(!StringUtils.isEmpty(authHeader));
        Assert.assertTrue(authHeader.startsWith(SecurityConstants.TOKEN_PREFIX));
        Assert.assertNotNull(authCookie);
        Assert.assertTrue(authCookie.getValue().startsWith(authCookie.getValue()));
    }
}