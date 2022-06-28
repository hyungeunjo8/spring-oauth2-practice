package com.weverse.springoauth2practice.security.jwt;

import com.weverse.springoauth2practice.security.CustomAuthenticationToken;
import com.weverse.springoauth2practice.security.Token;
import com.weverse.springoauth2practice.security.UserAuthority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;

public class Jwt {
    private final String secretKey;

    private static final long TOKEN_VALID_TIME = 1000L * 60L * 10L;
    private static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60L * 60L * 24L * 30L;

    public Jwt(String secretKey) {
        this.secretKey = secretKey;
    }

    public Token create(String uid, UserAuthority userAuthority) {
        Claims claims = Jwts.claims().setSubject(uid);
        claims.put("role", userAuthority.getAuthority());

        Date now = new Date();
        return new Token(
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

    public Authentication getAuthentication(String token) throws JwtInvalidException {
        if (token == null) {
            throw new JwtInvalidException();
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token.substring("Bearer ".length()));
        verify(claimsJws);
        return new CustomAuthenticationToken(claimsJws.getBody().getSubject(), List.of(new SimpleGrantedAuthority(claimsJws.getBody().get("role", String.class))));
    }

    private void verify(Jws<Claims> claimsJws) throws JwtInvalidException {
        boolean valid = claimsJws.getBody().getExpiration().after(new Date());
        if (!valid) {
            throw new JwtInvalidException();
        }
    }
}
