package com.muyuan.member.application.service;

import com.muyuan.member.domains.vo.UserVO;
import com.muyuan.member.interfaces.to.UserTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserApplicationService {

    /**
     * 登录获取用户信息 内部RPC
     * @param username
     * @return
     */
    UserTO getUserByUsername(String username);

    /**
     * 获取权限
     * @param roleCodes
     * @return
     */
    Set<String> getMenuPermissionByRoleCodes(List<String> roleCodes);

    /**
     * 获取用户信息
     * @return
     */
    Optional<UserVO> getUserInfo();
}
