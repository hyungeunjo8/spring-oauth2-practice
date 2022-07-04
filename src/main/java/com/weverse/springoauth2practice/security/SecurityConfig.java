package com.weverse.springoauth2practice.security;

import com.weverse.springoauth2practice.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
public class SecurityConfig {

    public SecurityConfig(CustomOauth2UserService customOauth2UserService, CustomOauth2SuccessHandler customOauth2SuccessHandler, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customOauth2UserService = customOauth2UserService;
        this.customOauth2SuccessHandler = customOauth2SuccessHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    private final CustomOauth2UserService customOauth2UserService;
    private final CustomOauth2SuccessHandler customOauth2SuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.authorizeRequests()
                .mvcMatchers("/authorization/multi-by-configure")
                .hasAnyAuthority(UserAuthority.ROLES.USER, UserAuthority.ROLES.MANAGER);

        httpSecurity.authorizeRequests()
                .mvcMatchers("/refresh-token")
                .permitAll();

        httpSecurity.authorizeRequests()
                .anyRequest()
                .authenticated();

        httpSecurity.oauth2Login()
                .userInfoEndpoint()
                .userService(customOauth2UserService)
                .and()
                .successHandler(customOauth2SuccessHandler)
                .permitAll();

        httpSecurity.addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class);

        return httpSecurity.build();
    }
}
