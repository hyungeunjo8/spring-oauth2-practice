package com.weverse.springoauth2practice.security.jwt;

import com.weverse.springoauth2practice.security.CustomAuthenticationToken;
import com.weverse.springoauth2practice.security.TokenDto;
import com.weverse.springoauth2practice.security.UserAuthority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;

public record Jwt(String secretKey) {
    private static final long TOKEN_VALID_TIME = 1000L * 60L * 10L;
    private static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60L * 60L * 24L * 30L;

    private static final String ROLE = "role";

    public TokenDto create(String uid, UserAuthority userAuthority) {
        Claims claims = Jwts.claims().setSubject(uid);
        claims.put(ROLE, userAuthority.getAuthority());

        Date now = new Date();
        return new TokenDto(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact());
    }

    public Authentication getAuthentication(String token) throws InvalidTokenException {
        String jwt = new BearerType(token).jwt();
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(jwt);
        verify(claimsJws);
        return new CustomAuthenticationToken(
                claimsJws.getBody().getSubject(),
                List.of(new SimpleGrantedAuthority(claimsJws.getBody().get("role", String.class)))
        );
    }

    private void verify(Jws<Claims> claimsJws) throws InvalidTokenException {
        boolean valid = claimsJws.getBody().getExpiration().after(new Date());
        if (!valid) {
            throw new InvalidTokenException();
        }
    }
}
