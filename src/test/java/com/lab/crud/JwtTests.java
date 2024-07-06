package com.lab.crud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab.crud.pojo.User;
import com.lab.crud.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

//@SpringBootTest
class JwtTests {
    private static final Logger log = LoggerFactory.getLogger(JwtTests.class);
    private final JwtUtils jwtutils = new JwtUtils();

    @Test
    void getJwtTest() throws JsonProcessingException {
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        Map<String, String> token = Map.of("token", jwtutils.getJwt(user.getId(), user.getUsername(), 15));
        System.out.println(new ObjectMapper().writeValueAsString(token));
    }

    @Test
    void parseJwtTest() throws JsonProcessingException {
//        User user = new User();
//        user.setId(1);
//        user.setUsername("admin");
//        String token = jwtutils.getJwt(user.getId(), user.getUsername(), 15);
//        Claims payLoad =jwtutils.parseJwt(token);
//        System.out.println(new ObjectMapper().writeValueAsString(payLoad));
    }
}
