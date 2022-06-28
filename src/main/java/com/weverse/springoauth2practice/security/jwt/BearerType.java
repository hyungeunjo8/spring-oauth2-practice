package com.weverse.springoauth2practice.security.jwt;

public record BearerType(String token) {

    private static final String PREFIX = "Bearer ";

    private void isValid() throws InvalidTokenException {
        if (token == null || token.isBlank()) {
            throw new InvalidTokenException();
        }

        if (!token.contains(PREFIX)) {
            throw new InvalidTokenException();
        }
    }

    public String jwt() throws InvalidTokenException {
        isValid();
        return token.substring("Bearer ".length());
    }
}
