package com.muyuan.member.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户编号
     */
    private long userNo;

    /**
     * 账号名唯一用于登录
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密salt
     */
    private String salt;

    /**
     * 加密key
     */
    private String encryptKey;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 是否删除（0-否 1-是）
     */
    private boolean isDelete;

    /**
     * 账号状态 0-正常 1-锁定
     */
    private short status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人编号
     */
    private long updateUserNo;

    /**
     * 修改人编号
     */
    private long createUserNo;

}
