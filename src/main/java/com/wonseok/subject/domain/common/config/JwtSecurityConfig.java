package com.wonseok.subject.domain.common.config;

import com.wonseok.subject.domain.common.exception.JwtTokenException;
import com.wonseok.subject.domain.common.token.TokenProvider;
import com.wonseok.subject.domain.common.filter.JwtAuthorizationFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private TokenProvider tokenProvider;
    public JwtSecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws JwtTokenException {
        http
            .addFilterBefore(
                new JwtAuthorizationFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class
            );
    }
}
