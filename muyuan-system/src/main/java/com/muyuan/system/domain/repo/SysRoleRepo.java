package com.muyuan.system.domain.repo;

import com.muyuan.system.domain.model.DictData;
import com.muyuan.system.domain.model.SysRole;
import com.muyuan.system.domain.model.SysRoleMenu;

import java.util.List;
import java.util.Map;

public interface SysRoleRepo {

    List<SysRole> selectRoleByUserId(Long userId);

    List<SysRole> select(Map params);

    SysRole selectOne(Map params);

    int insert(SysRole sysRole);

    int batchInsert(List<SysRoleMenu> sysRoleMenus);

    int updateById(SysRole sysRole);

    int deleteMenuBy(SysRoleMenu entity,String... fieldNames);

    void deleteCache(SysRole sysRole);
}
