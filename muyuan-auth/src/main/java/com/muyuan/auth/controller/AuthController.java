package com.muyuan.auth.controller;

import com.muyuan.common.bean.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController()
@Tag(name = "登录接口")
public interface AuthController {

    @GetMapping("/captchaImage")
    @Operation(summary = "获取登录验证码")
    Result captchaImage(HttpServletRequest httpServletRequest) throws IOException;


    @GetMapping("/cancel")
    @Operation(summary = "注销登录")
    Result logout();
}
