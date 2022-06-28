package com.weverse.springoauth2practice.security;

import com.weverse.springoauth2practice.security.jwt.Jwt;
import com.weverse.springoauth2practice.security.jwt.JwtConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@Component
public class CustomOauth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtConfiguration jwtConfiguration;

    public CustomOauth2SuccessHandler(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        Token token = new Jwt(jwtConfiguration.getJwtSecretKey()).create(email, getRole());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(token.toJson());
    }

    private UserAuthority getRole() {
        UserAuthority[] values = UserAuthority.values();
        Random rand = new Random();
        return values[rand.nextInt(values.length)];
    }
}
