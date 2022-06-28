package com.weverse.springoauth2practice.token;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @GetMapping("refresh-token")
    public void refreshToken() {
        // verify refresh token, create token
    }

    @GetMapping("/token/expired")
    public ResponseEntity<Void> auth() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
