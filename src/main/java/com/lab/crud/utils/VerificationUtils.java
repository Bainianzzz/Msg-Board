package com.lab.crud.utils;

import com.lab.crud.interceptor.AuthInterceptor;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Component
public class VerificationUtils {
    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    //对于PUT、DELETE方法，对用户身份进行额外的验证
    public boolean IdentityVerification(Claims payload, HttpServletRequest request, HttpServletResponse response) {
        switch (RequestMethod.valueOf(request.getMethod())) {
            case PUT, DELETE:
                String userId = payload.get("id").toString();
                String pathInfo = request.getRequestURI();
                String[] path = pathInfo.split("/");
                return Verification4User(userId, path, response);
            default:
                return true;
        }
    }

    //比较路径参数中的用户id与token中的用户id是否一致
    private boolean Verification4User(String userId, String[] path, HttpServletResponse response) {
        String idInPath = path[path.length - 1];
        if (!userId.equals(idInPath)) {
            log.error("userId doesn't match the id in path");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            try {
                response.getWriter().write("userId doesn't match the id in path");
            } catch (IOException err) {
                log.error(err.getMessage());
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            return false;
        }
        return true;
    }
}
