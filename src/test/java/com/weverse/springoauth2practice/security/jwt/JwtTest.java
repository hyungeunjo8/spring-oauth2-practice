package com.weverse.springoauth2practice.security.jwt;

import com.weverse.springoauth2practice.security.Token;
import com.weverse.springoauth2practice.security.UserAuthority;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class JwtTest {

    @Test
    void create() throws JwtInvalidException {
        Token token = new Jwt("12345678").create("abc@abcd.com", UserAuthority.ADMIN);

        System.out.println("Bearer" + token.token());

        boolean authenticated = new Jwt("12345678").getAuthentication("Bearer " + token.token()).isAuthenticated();

        Token token2 = new Jwt("42345678").create("abc@abcd.com", UserAuthority.USER);

        boolean authenticated1 = new Jwt("12345678").getAuthentication("Bearer " + token2.token()).isAuthenticated();

        System.out.println(authenticated);
    }

    @Test
    void createTokens() {
        Arrays.stream(UserAuthority.values())
                .forEach(userAuthority -> {
                    System.out.println(userAuthority.getAuthority() + ": " +
                            "Bearer " + new Jwt("12345678").create("abc@abcd.com", userAuthority).token()
                    );
                });

    }
}