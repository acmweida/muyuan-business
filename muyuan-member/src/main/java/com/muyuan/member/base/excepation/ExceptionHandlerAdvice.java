package com.muyuan.member.base.excepation;

import com.alibaba.fastjson.JSONObject;
import com.muyuan.common.enums.CodeMessage;
import com.muyuan.common.exception.MuyuanException;
import com.muyuan.common.exception.MuyuanExceptionHandler;
import com.muyuan.common.result.Result;
import com.muyuan.common.result.ResultUtil;
import org.springframework.beans.propertyeditors.FileEditor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        JSONObject errorInfo = new JSONObject();
        for (ObjectError error : allErrors) {
            System.out.println(error);
            errorInfo.put(((FieldError)error).getField(),((FieldError)error).getDefaultMessage());
        }

        return ResultUtil.renderFail(CodeMessage.ARGUMENT_EEORR,errorInfo);
    }

    @ExceptionHandler(MuyuanException.class)
    public Result muyuanExceptionHaneler(MuyuanException e) {
        if (e instanceof MuyuanExceptionHandler) {
            MuyuanExceptionHandler handler = (MuyuanExceptionHandler) e;
            return handler.handle(e);
        }
        return unknowRuntimeException(e);
    }

    @ExceptionHandler(RuntimeException.class)
    public Result unknowRuntimeException(RuntimeException e) {
        return ResultUtil.renderError(e.getStackTrace());
    }

}
