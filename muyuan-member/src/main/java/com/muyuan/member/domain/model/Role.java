package com.muyuan.member.domain.model;

import lombok.Data;

/**
 * @ClassName Role
 * Description 角色 t_role
 * @Author 2456910384
 * @Date 2021/12/24 10:17
 * @Version 1.0
 */
@Data
public class Role {

    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 名称描述
     */
    private String nameDesc;

    /**
     * 状态 0-正常 1-删除
     */
    private short state;

    /**
     * 父角色ID
     */
    private Long parentId;
}
