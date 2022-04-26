package com.muyuan.system.domain.service.impl;

import com.muyuan.common.core.constant.GlobalConst;
import com.muyuan.common.core.constant.auth.SecurityConst;
import com.muyuan.common.core.util.StrUtil;
import com.muyuan.common.mybatis.jdbc.crud.SqlBuilder;
import com.muyuan.system.domain.entity.SysRoleEntity;
import com.muyuan.system.domain.factories.SysMenuFactory;
import com.muyuan.system.domain.repo.SysMenuRepo;
import com.muyuan.system.domain.service.SysMenuDomainService;
import com.muyuan.system.domain.model.SysMenu;
import com.muyuan.system.interfaces.dto.SysMenuDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @ClassName SysMenuServiceImpl
 * Description 菜单服务实现
 * @Author 2456910384
 * @Date 2022/4/15 14:17
 * @Version 1.0
 */
@Component
@AllArgsConstructor
@Slf4j
public class SysMenuDomainServiceImpl implements SysMenuDomainService {

    private SysMenuRepo sysMenuRepo;

    // ##############################  query ########################## //

    @Override
    public String checkMenuNameUnique(SysMenu sysMenu) {
        Long id = null == sysMenu.getId() ? 0 : sysMenu.getId();
        sysMenu = sysMenuRepo.selectOne(new SqlBuilder(SysMenu.class)
                .eq("name",sysMenu.getName())
                .eq("parentId",sysMenu.getParentId())
                .build());
        if (null != sysMenu && id.equals(sysMenu.getId())) {
            return GlobalConst.NOT_UNIQUE;
        }
        return GlobalConst.UNIQUE;
    }

    /**
     * 通过角色名称查询权限
     *
     * @param roleNames
     * @return
     */
    @Override
    public Set<String> selectMenuPermissionByRoleNames(List<String> roleNames) {
        Set<String> perms = new HashSet<>();
        if (SysRoleEntity.isAdmin(roleNames)) {
            perms.add(SecurityConst.ALL_PERMISSION);
        } else {
            List<String> permList = sysMenuRepo.selectMenuPermissionByRoleNames(roleNames);
            for (Iterator<String> iterator = permList.iterator(); iterator.hasNext(); ) {
                perms.add(iterator.next());
            }
        }
        return perms;
    }

    /**
     * 通过角色名称查询目录 菜单
     *
     * @param roleNames
     * @return
     */
    @Override
    public List<SysMenu> selectMenuByRoleNames(List<String> roleNames) {
        if (ObjectUtils.isEmpty(roleNames)) {
            return Collections.EMPTY_LIST;
        }
        return sysMenuRepo.selectMenuByRoleNames(roleNames);
    }

    @Override
    public List<SysMenu> list(SysMenuDTO sysMenuDTO) {
        SqlBuilder sqlBuilder = new SqlBuilder(SysMenu.class);
        if (StrUtil.isNotBlank(sysMenuDTO.getName())) {
            sqlBuilder.eq("name", sysMenuDTO.getName());
        }
        if (StrUtil.isNotBlank(sysMenuDTO.getStatus())) {
            sqlBuilder.eq("status", sysMenuDTO.getStatus());
        }
        List<SysMenu> list = sysMenuRepo.select(sqlBuilder.build());
        return list;
    }

    /**
     * 查询
     * condition
     *      id
     *      name
     * @param sysMenu
     * @return
     */
    public SysMenu get(SysMenu sysMenu) {
        SqlBuilder sqlBuilder = new SqlBuilder(SysMenu.class);
        sqlBuilder.eq("status", "0");
        if (!Objects.isNull(sysMenu.getId())) {
            sqlBuilder.eq("id", sysMenu.getId());
        }
        if (!Objects.isNull(sysMenu.getName())) {
            sqlBuilder.eq("name", sysMenu.getName());
        }
        return sysMenu = sysMenuRepo.selectOne(sqlBuilder.build());
    }

    @Override
    public Optional<SysMenu> get(String id) {
        SysMenu sysMenu = get(new SysMenu(Long.valueOf(id)));
        if (null != sysMenu) {
            return Optional.of(sysMenu);
        }
        return Optional.empty();
    }

    // ##############################  query ########################## //

    @Override
    public int add(SysMenuDTO sysMenuDTO) {
        SysMenu sysMenu = SysMenuFactory.newSysMenu(sysMenuDTO);
        return sysMenuRepo.insert(sysMenu);
    }

    @Override
    public int update(SysMenuDTO sysMenuDTO) {
        SysMenu sysMenu = SysMenuFactory.updateSysMenu(sysMenuDTO);
        return sysMenuRepo.updateById(sysMenu);
    }



    @Override
    public int deleteById(String... ids) {
        if (ObjectUtils.isEmpty(ids)) {
            return 0;
        }
        return sysMenuRepo.deleteById(ids);
    }


}
