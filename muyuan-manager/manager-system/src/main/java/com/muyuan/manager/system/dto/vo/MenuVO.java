package com.muyuan.manager.system.dto.vo;

import com.muyuan.common.web.util.SecurityUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName Menu
 * Description 菜单权限表
 * @Author 2456910384
 * @Date 2022/1/27 16:38
 * @Version 1.0
 */
@Data
public class MenuVO {

    private Long id;

    private String platformType;

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

    private List<MenuVO> children = new ArrayList<>(0);

    public MenuVO() {
    }

    public MenuVO(Long id) {
        this.id = id;
    }

    public MenuVO(String name) {
        this.name = name;
    }

    /**
     *  构建一个系统菜单 并初始化
     * @return
     */
    public MenuVO update()  {
        this.setUpdateTime(new Date());
        this.setUpdateBy(SecurityUtils.getUserId());
        return this;
    }

}
