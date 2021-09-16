package com.muyuan.common.enums;

public enum ResponseCode {
    SUCCESS(0,"SUCCESS","操作成功"),
    FAIL(400,"FAIL","操作失败"),
    AUTH_FAIL(401,"FAIL","没有权限"),
    ARGUMENT_EEORR(402,"FAIL","参数错误"),
    UNAUTHORIZED(405,"FAIL","没有登录或者token失效"),
    ERROR(500,"ERROR","未知异常，请联系管理员！");



    private int code;
    private String type;
    private String msg;

    ResponseCode(int code, String type,String msg) {
        this.code = code;
        this.type = type;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }


    public String getType() {
        return type;
    }
}
