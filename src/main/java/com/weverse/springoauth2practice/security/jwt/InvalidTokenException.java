package com.weverse.springoauth2practice.security.jwt;

public class InvalidTokenException extends Exception {
    public InvalidTokenException() {
        super("유효 하지 않은 token");
    }
}
