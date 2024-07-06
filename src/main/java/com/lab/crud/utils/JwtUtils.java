package com.lab.crud.utils;

import com.lab.crud.exception.TokenDyingException;
import io.jsonwebtoken.*;
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

    //判断token是否符合格式
    public boolean availableToken(String... tokens) {
        for (String token : tokens) {
            if (token == null || !token.startsWith("Bearer ")) {
                return false;
            }
        }
        return true;
    }

    // 检验token，若属于濒死状态或正常过期状态，校验refresh_token，若成功则刷新token
    public String renewToken(String refreshToken) throws RuntimeException {
        Claims newClaims = parseJwt(refreshToken);
        Integer id = (Integer) newClaims.get("id");
        String username = (String) newClaims.get("username");
        return getJwt(id, username, 15);
    }

    //生成token
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

    //校验token，并判断其是否过期或濒死
    public Claims parseJwt(String jwt) {
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwt);
        if (claims.getPayload().getExpiration().before(new Date(System.currentTimeMillis() - 1000 * 60 * 5))) {
            throw new TokenDyingException();
        }
        return claims.getPayload();
    }
}
