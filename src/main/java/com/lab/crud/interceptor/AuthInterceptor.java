package com.lab.crud.interceptor;

import com.lab.crud.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler)
            throws Exception {
        log.info("preHandle: {}", request.getRequestURI());
        // 检查token是否将要过期或正常过期（refresh_token仍有效），若是则更新token
        // 检验token和refresh_token是否都在请求头中
        String token = request.getHeader("Authorization");
        String refreshToken = request.getHeader("refresh_token");
        if (token == null || refreshToken == null) {
            response.setStatus(HttpStatus.PRECONDITION_REQUIRED.value());
            response.getWriter().write("The request does not include the token or refresh_token in right field.");
            return false;
        }
        if (!token.startsWith("Bearer ") || !refreshToken.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("The format of the token and refresh_token fields is incorrect");
            return false;
        }
        token = token.replace("Bearer ", "");
        refreshToken = refreshToken.replace("Bearer ", "");
        // 检验token，若属于濒死状态或正常过期状态，校验refresh_token，若成功则刷新token
        try {
            jwtUtils.parseJwt(token);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            if (e instanceof MalformedJwtException || e instanceof SignatureException) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(e.getMessage());
                return false;
            } else if (e instanceof ExpiredJwtException) {
                // 校验refresh_token，尝试刷新token
                try {
                    Claims claims = jwtUtils.parseJwt(refreshToken);
                    Integer id = (Integer) claims.get("id");
                    String username = (String) claims.get("username");
                    token = jwtUtils.getJwt(id, username, 15);
                    response.addHeader("Authorization", "Bearer " + token);
                    log.info("{} refreshed token", username);
                } catch (RuntimeException err) {
                    log.error(err.getMessage());
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write(err.getMessage());
                    return false;
                }
            }
        }
        return true;
    }
}


