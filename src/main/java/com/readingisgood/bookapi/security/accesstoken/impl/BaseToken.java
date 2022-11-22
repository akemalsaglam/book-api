package com.readingisgood.bookapi.security.accesstoken.impl;


import com.readingisgood.bookapi.security.RoleType;
import com.readingisgood.bookapi.security.accesstoken.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class BaseToken implements Token {

    private static final String ISSUER = "https://readingisgood.com";
    private static final String SIGNING_KEY = "readingisgood-access-token-2022";
    private static final SignatureAlgorithm TOKEN_SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private final long tokenValiditySeconds;

    public BaseToken(long tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }

    @Override
    public String generateToken(UserDetails user, RoleType roleType) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("scopes", List.of(new SimpleGrantedAuthority(roleType.value)));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(ISSUER)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValiditySeconds * 1000))
                .signWith(TOKEN_SIGNATURE_ALGORITHM, SIGNING_KEY)
                .compact();
    }

    @Override
    public Boolean validateToken(String token, String userName) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userName) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
    }
}
