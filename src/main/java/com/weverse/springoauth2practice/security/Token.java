package com.weverse.springoauth2practice.security;

public record Token(String token, String refreshToken) {

    public String toJson() {
        return "{\"token\": \"" + token + "\", \"refreshToken\": \"" + refreshToken + "\"}";
    }
}
