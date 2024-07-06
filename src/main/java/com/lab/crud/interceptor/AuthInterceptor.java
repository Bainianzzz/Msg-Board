package com.lab.crud.interceptor;

import com.lab.crud.exception.TokenDyingException;
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
    // 检查token是否将要过期或正常过期（refresh_token仍有效），若是则更新token
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler)
            throws Exception {
        log.info("preHandle: {}", request.getRequestURI());
        // 检验token和refresh_token是否都在请求头中并符合格式
        String token = request.getHeader("Authorization");
        String refreshToken = request.getHeader("refresh_token");
        if (!jwtUtils.availableToken(token, refreshToken)) return false;
        token = token.replace("Bearer ", "");
        // 检验token，若属于濒死状态或正常过期状态，校验refresh_token，若成功则刷新token
        try {
            jwtUtils.parseJwt(token);
            return true;
        } catch (RuntimeException e) {
            if (e instanceof MalformedJwtException || e instanceof SignatureException) {
                log.error(e.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(e.getMessage());
                return false;
            } else if (e instanceof ExpiredJwtException || e instanceof TokenDyingException) {
                // 校验refresh_token，尝试刷新token
                try {
                    token = jwtUtils.renewToken(refreshToken.replace("Bearer ", ""));
                    response.addHeader("Authorization", "Bearer " + token);
                    log.info("refreshed token");
                    return true;
                } catch (RuntimeException err) {
                    log.error(err.getMessage());
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write(err.getMessage());
                    return false;
                }
            }
            log.error(e.getMessage());
            return false;
        }
    }
}


