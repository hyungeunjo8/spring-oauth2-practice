package com.weverse.springoauth2practice.security.jwt;

public class JwtInvalidException extends Exception {
    public JwtInvalidException() {
        super("유효 하지 않은 token");
    }
}
