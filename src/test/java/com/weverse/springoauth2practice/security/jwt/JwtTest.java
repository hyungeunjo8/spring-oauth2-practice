package com.weverse.springoauth2practice.security.jwt;

import com.weverse.springoauth2practice.security.Token;
import com.weverse.springoauth2practice.security.UserAuthority;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class JwtTest {

    @Test
    void createAndVerifyTest() throws JwtInvalidException {
        String securityKey = UUID.randomUUID().toString();
        String uid = UUID.randomUUID().toString();
        Token token = new Jwt(securityKey).create(uid, UserAuthority.ADMIN);
        new Jwt(securityKey).getAuthentication("Bearer " + token.token());
    }
}