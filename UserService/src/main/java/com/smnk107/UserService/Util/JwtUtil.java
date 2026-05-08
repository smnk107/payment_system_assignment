package com.smnk107.UserService.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "nifvnindfmjioncvjinslkcjnodnvsndkfmkmxsokednsnidncjsnomnvjnijnvov";
    private SecretKey createSecretKey(String skey)
    {
        return Keys.hmacShaKeyFor(skey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(createSecretKey(SECRET))
                .compact();



    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) getClaims(token).get("role");
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(createSecretKey(SECRET))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
