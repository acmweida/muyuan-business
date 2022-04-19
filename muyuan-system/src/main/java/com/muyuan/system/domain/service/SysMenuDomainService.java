package com.muyuan.system.domain.service;

import com.muyuan.system.domain.model.SysMenu;
import com.muyuan.system.interfaces.dto.SysMenuDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @ClassName MenuService 接口
 * Description MenuService
 * @Author 2456910384
 * @Date 2022/2/14 9:38
 * @Version 1.0
 */
public interface SysMenuDomainService {

    /**
     * 列表查询
     * @param sysMenuDTO
     * @return
     */
    List<SysMenu> list(SysMenuDTO sysMenuDTO);

    /**
     * 根据Id获取菜单详情
     * @param id
     * @return
     */
    Optional<SysMenu> get(String id);

    /**
     * 获取权限信息
     * @param roleNames
     * @return
     */
    Set<String> selectMenuPermissionByRoleNames(List<String> roleNames);

    /**
     * 菜单添加
     * @param sysMenuDTO
     * @return
     */
    int add(SysMenuDTO sysMenuDTO);

    String checkMenuNameUnique(SysMenu sysMenu);
}