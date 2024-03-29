package com.muyuan.store.system.domains.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName Menu
 * Description 菜单权限表
 * @Author 2456910384
 * @Date 2022/1/27 16:38
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
public class Menu {

    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String query;

    /**
     * 是否为外链（0是 1否）
     */
    private String frame;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String type;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    private String status;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;


    /** 是否缓存（0缓存 1不缓存） */
    private String cache;

    /**
     * 修改时间
     */
    private Date updateTime;

    private Long createBy;

    private Long updateBy;

    public Menu() {
    }

    public Menu(Long id) {
        this.id = id;
    }

    public Menu(String name) {
        this.name = name;
    }
}
