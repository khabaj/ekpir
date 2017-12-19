package com.khabaj.ekpir.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static com.khabaj.ekpir.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthFilter extends BasicAuthenticationFilter {

    public JWTAuthFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
                                    FilterChain chain) throws IOException, ServletException {

        String authToken = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isEmpty(authToken)) {
            Optional<Cookie> authCookie = Optional.ofNullable(WebUtils.getCookie(httpRequest, HttpHeaders.AUTHORIZATION));
            authToken = authCookie.map(Cookie::getValue).orElse("");
        }

        if (StringUtils.hasText(authToken) && authToken.startsWith(TOKEN_PREFIX)) {
            authToken = authToken.replace(TOKEN_PREFIX, "");
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                String username = JWTUtils.getSubjectFromToken(authToken);

                if (StringUtils.hasText(username)) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(httpRequest, httpResponse);
    }
}
