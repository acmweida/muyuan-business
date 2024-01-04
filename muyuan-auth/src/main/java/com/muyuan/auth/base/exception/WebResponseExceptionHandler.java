package com.muyuan.auth.base.exception;

import com.muyuan.common.bean.Result;
import com.muyuan.common.core.enums.ResponseCode;
import com.muyuan.common.core.util.JSONUtil;
import com.muyuan.common.core.util.ResultUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @ClassName CustomWebResponseExceptionTranslator
 * Description 异常处理器
 * @Author 2456910384
 * @Date 2022/1/26 16:56
 * @Version 1.0
 */
@Slf4j
public class WebResponseExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler, AuthenticationFailureHandler {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        log.error(authException.toString());
        Result result = null;
        if (CaptchaMatchFailException.class.isAssignableFrom(authException.getClass())) {
            result =  ResultUtil.fail(((CaptchaMatchFailException) authException).getResponseCode());
        }
        else {
            result =  ResultUtil.fail(ResponseCode.AUTHORIZED_ERROR.getCode(),ResponseCode.AUTHORIZED_ERROR.getMsg());
        }

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(Objects.requireNonNull(JSONUtil.toJsonString(result)));
        response.getWriter().flush();
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        log.error(accessDeniedException.toString());
        Result result = null;
        AuthException.class.isAssignableFrom(accessDeniedException.getClass());
        result = ResultUtil.error(ResponseCode.AUTHORIZED_ERROR.getCode(), ResponseCode.AUTHORIZED_ERROR.getMsg());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(Objects.requireNonNull(JSONUtil.toJsonString(result)));
        response.getWriter().flush();
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        log.error(exception.toString());
        Result result = null;
        if (AuthException.class.isAssignableFrom(exception.getClass())) {
            result =  ResultUtil.fail(((AuthException) exception).getResponseCode());
        } else if (BadCredentialsException.class.isAssignableFrom(exception.getClass())) {
            result = ResultUtil.fail(ResponseCode.LOGIN_INFO_ERROR);
        }
        else {
            result =  ResultUtil.error(ResponseCode.AUTHORIZED_ERROR.getCode(),ResponseCode.AUTHORIZED_ERROR.getMsg());
        }

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(Objects.requireNonNull(JSONUtil.toJsonString(result)));
        response.getWriter().flush();
    }
}