package com.lab.crud.utils;

import com.lab.crud.interceptor.AuthInterceptor;
import com.lab.crud.mapper.MessageMapper;
import com.lab.crud.pojo.Message;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Component
public class VerificationUtils {
    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
    @Autowired
    private MessageMapper messageMapper;

    //对于PUT、DELETE方法，对用户身份进行额外的验证
    public boolean IdentityVerification(Claims payload, HttpServletRequest request, HttpServletResponse response) {
        switch (RequestMethod.valueOf(request.getMethod())) {
            case PUT, DELETE:
                String userId = payload.get("id").toString();
                String pathInfo = request.getRequestURI();
                String[] path = pathInfo.split("/");
                return switch (path[1]) {
                    case "user" -> Verification4User(userId, path, response);
                    case "message" -> Verification4Message(userId, path, response);
                    default -> false;
                };
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

    private boolean Verification4Message(String userId, String[] path, HttpServletResponse response) {
        String messageIdInPath = path[path.length - 1];
        try {
            Message message = messageMapper.selectMessageById(Integer.parseInt(messageIdInPath));
            if (message == null) {
                log.error("message not found");
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.getWriter().write("message not found");
                return false;
            } else {
                String uid = String.valueOf(message.getUid());
                if (!userId.equals(uid)) {
                    log.error("user is not allowed to modify or delete others' messages");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("you are not allowed to modify or delete others' messages");
                    return false;
                }
                return true;
            }
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } catch (IOException err) {
            log.error(err.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return false;
    }
}
