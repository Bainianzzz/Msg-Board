package com.lab.crud;

import com.lab.crud.pojo.User;
import com.lab.crud.utils.JwtUtils;
import org.junit.jupiter.api.Test;

//@SpringBootTest
class JwtTests {
    private final JwtUtils jwtutils = new JwtUtils();

    @Test
    void getJwtTest() {
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        System.out.println(jwtutils.getJwt(user));
    }
}
