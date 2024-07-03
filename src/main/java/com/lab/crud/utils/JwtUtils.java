package com.lab.crud.utils;

import com.lab.crud.pojo.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    SecretKey key = Jwts.SIG.HS512.key().build();
    private long cnt;

    public String getJwt(User user){
        Map<String,Object> claims=new HashMap<>();
        claims.put("id",user.getId());
        claims.put("username",user.getUsername());
        claims.put("issuedTime",System.currentTimeMillis());
        claims.put("cnt",cnt%1000000);
        cnt++;
        return Jwts.builder()
                .claims(claims)
                .signWith(Keys.hmacShaKeyFor(key.getEncoded()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .compact();
    }
}
