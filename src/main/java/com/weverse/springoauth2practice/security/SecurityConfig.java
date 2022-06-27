package com.weverse.springoauth2practice.security;

import com.weverse.springoauth2practice.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
public class SecurityConfig {

    public SecurityConfig(CustomOauth2UserService customOauth2UserService, CustomOauth2SuccessHandler customOauth2SuccessHandler,
                          CustomOauth2FailHandler customOauth2FailHandler, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customOauth2UserService = customOauth2UserService;
        this.customOauth2SuccessHandler = customOauth2SuccessHandler;
        this.customOauth2FailHandler = customOauth2FailHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    private final CustomOauth2UserService customOauth2UserService;
    private final CustomOauth2SuccessHandler customOauth2SuccessHandler;
    private final CustomOauth2FailHandler customOauth2FailHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeRequests()
                .mvcMatchers("/users").permitAll()
                .anyRequest()
                .authenticated();


        httpSecurity.oauth2Login()
                .userInfoEndpoint()
                .userService(customOauth2UserService)
                .and()
                .successHandler(customOauth2SuccessHandler)
                .failureHandler(customOauth2FailHandler)
                .permitAll();

        httpSecurity.logout()
                .deleteCookies("JSESSIONID");

        httpSecurity.addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class);

        return httpSecurity.build();
    }
}
