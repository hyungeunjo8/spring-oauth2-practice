package com.weverse.springoauth2practice.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    public String getJwtSecretKey() {
        return jwtSecretKey;
    }
}
