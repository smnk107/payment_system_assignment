package com.smnk107.APIGateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class JwtService {

    private String jwtKey = "nifvnindfmjioncvjinslkcjnodnvsndkfmkmxsokednsnidncjsnomnvjnijnvov";

    private SecretKey createSecretKey(String skey)
    {
        return Keys.hmacShaKeyFor(skey.getBytes(StandardCharsets.UTF_8));
    }
    public Long getUserIdFromToken(String token)
    {
        System.out.println(

                "token :"+token
        );
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .verifyWith(createSecretKey(jwtKey))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();


            System.out.println(Integer.valueOf(claims.getSubject()));
            return Long.valueOf(claims.getSubject());

        }
        catch (Exception e)
        {
            System.out.println("Exception " +e.getMessage());
        }

        System.out.println(Integer.valueOf(claims.getSubject()));
        return Long.valueOf(claims.getSubject());
    }
}

