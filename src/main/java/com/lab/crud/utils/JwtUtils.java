package com.lab.crud.utils;

import com.lab.crud.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    SecretKey key = Jwts.SIG.HS512.key().build();
    private long cnt;

    public String getJwt(int id, String username, int expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", username);
        claims.put("issuedTime", System.currentTimeMillis());
        claims.put("cnt", cnt % 1000000);
        cnt++;
        return Jwts.builder()
                .claims(claims)
                .signWith(Keys.hmacShaKeyFor(key.getEncoded()))
                .expiration(new Date(System.currentTimeMillis() + (1000L * 60) * expiration))
                .compact();
    }

    public Claims parseJwt(String jwt) {
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt);
        return claims.getPayload();
    }
}
