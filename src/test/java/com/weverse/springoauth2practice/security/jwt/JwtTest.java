package com.weverse.springoauth2practice.security.jwt;

import com.weverse.springoauth2practice.security.TokenDto;
import com.weverse.springoauth2practice.security.UserAuthority;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class JwtTest {

    @Test
    void createAndVerifyTest() throws InvalidTokenException {
        String securityKey = UUID.randomUUID().toString();
        String uid = UUID.randomUUID().toString();
        TokenDto tokenDto = new Jwt(securityKey).create(uid, UserAuthority.ADMIN);
        new Jwt(securityKey).getAuthentication("Bearer " + tokenDto.token());
    }
}