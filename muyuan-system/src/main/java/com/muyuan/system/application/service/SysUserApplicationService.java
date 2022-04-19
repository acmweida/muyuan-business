package com.muyuan.system.application.service;

import com.muyuan.system.application.vo.SysUserVO;
import com.muyuan.system.interfaces.dto.RegisterDTO;
import com.muyuan.system.interfaces.dto.SysUserDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 用户应用接口
 */
public interface SysUserApplicationService {

    /**
     * 登录获取用户信息 内部RPC
     * @param username
     * @return
     */
    SysUserDTO getUserByUsername(String username);

    /**
     * 获取用户信息
     * @return
     */
    Optional<SysUserVO> getUserInfo();


    /**
     * 获取权限
     * @param roleIds
     * @return
     */
    Set<String> getMenuPermissionByRoleNames(List<String> roleIds);

}