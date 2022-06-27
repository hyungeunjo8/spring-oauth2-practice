package com.weverse.springoauth2practice.security.jwt;

import com.weverse.springoauth2practice.security.CustomOauth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

class JsonWebToken {

    private final HttpServletRequest request;
    private final String jwtSecretKey;

    JsonWebToken(HttpServletRequest request, String jwtSecretKey) {
        this.request = request;
        this.jwtSecretKey = jwtSecretKey;
    }

    public Authentication getAuthentication() {
        Collection<? extends GrantedAuthority> authorities = List.of();
        CustomOauth2User customOauth2User = new CustomOauth2User();
        return new OAuth2AuthenticationToken(customOauth2User, authorities, "google");
    }
}
