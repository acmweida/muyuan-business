package com.muyuan.common.web.aspect;

import com.muyuan.common.core.enums.ResponseCode;
import com.muyuan.common.core.util.ResultUtil;
import com.muyuan.common.redis.util.TokenUtil;
import com.muyuan.common.web.annotations.Repeatable;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @ClassName RepeatableRequestAdvice
 * Description 接口幂等性保证
 * @Author 2456910384
 * @Date 2021/10/15 10:01
 * @Version 1.0
 */
@Aspect
@Component
@AllArgsConstructor
public class RepeatableRequestAspect {


    @Around("@annotation(repeatable)")
    public Object repeatableAdvice(ProceedingJoinPoint pjp, Repeatable repeatable) throws Throwable {
        String token = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest
                ().getParameter(repeatable.varName());
        if (ObjectUtils.isEmpty(token)) {
            return ResultUtil.fail(ResponseCode.ARGUMENT_ERROR.getCode(),"TOKEN未传递");
        }

        if (!TokenUtil.check(repeatable.tokenType(),token)) {
            return ResultUtil.fail(ResponseCode.REPEATABLE_REQUEST_FAIL);
        }

         Object result = pjp.proceed();

         return result;

    }

}
