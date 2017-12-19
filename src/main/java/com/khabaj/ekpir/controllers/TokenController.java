package com.khabaj.ekpir.controllers;

import com.khabaj.ekpir.controllers.dto.AuthenticationRequest;
import com.khabaj.ekpir.security.JWTUtils;
import com.khabaj.ekpir.security.SecurityConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth/token")
public class TokenController {

    AuthenticationManager authenticationManager;

    public TokenController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void generateToken(@RequestBody @Valid AuthenticationRequest authenticationRequest,
                              @RequestParam(value = "setCookie", required = false) boolean setCookie,
                              HttpServletResponse response) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        String token = JWTUtils.generateToken(authenticationRequest.getUsername());

        if (setCookie) {
            Cookie authCookie = new Cookie(HttpHeaders.AUTHORIZATION, token);
            authCookie.setHttpOnly(true);
            response.addCookie(authCookie);
        }
        response.addHeader(HttpHeaders.AUTHORIZATION, SecurityConstants.TOKEN_PREFIX + token);
    }
}
