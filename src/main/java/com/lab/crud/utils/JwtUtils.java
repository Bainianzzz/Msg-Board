package com.lab.crud.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    @Value("${jwt.signingKey}")
    private String signingKey;
    private long cnt;

    private SecretKey getSecretKey() {
        byte[] encodedKey = Decoders.BASE64.decode(signingKey);
        return new SecretKeySpec(encodedKey, "HMACSHA256");
    }

    public String getJwt(int id, String username, int expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", username);
        claims.put("issuedTime", System.currentTimeMillis());
        claims.put("cnt", cnt % 1000000);
        cnt++;
        return Jwts.builder()
                .claims(claims)
                .signWith(Keys.hmacShaKeyFor(getSecretKey().getEncoded()))
                .expiration(new Date(System.currentTimeMillis() + (1000L * 60) * expiration))
                .compact();
    }

    public Claims parseJwt(String jwt) {
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwt);
        return claims.getPayload();
    }
}
