package com.muyuan.member.domain.service.impl;

import com.muyuan.common.core.constant.GlobalConst;
import com.muyuan.common.mybatis.jdbc.crud.SqlBuilder;
import com.muyuan.common.mybatis.jdbc.page.Page;
import com.muyuan.member.domain.factories.RoleFactory;
import com.muyuan.member.domain.model.Role;
import com.muyuan.member.domain.model.RoleMenu;
import com.muyuan.member.domain.model.User;
import com.muyuan.member.domain.repo.MenuRepo;
import com.muyuan.member.domain.repo.RoleRepo;
import com.muyuan.member.domain.repo.UserRepo;
import com.muyuan.member.domain.service.RoleDomainService;
import com.muyuan.member.interfaces.dto.RoleDTO;
import com.muyuan.member.interfaces.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName RoleQueryImpl
 * Description 角色域服务
 * @Author 2456910384
 * @Date 2021/12/24 11:04
 * @Version 1.0
 */
@AllArgsConstructor
@Service
@Slf4j
public class RoleDomainServiceImpl implements RoleDomainService {

    private RoleRepo roleRepo;

    private MenuRepo menuRepo;

    private UserRepo userRepo;

    /**
     * 根据用户id查询角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> getRoleByUserId(Long userId) {
        Assert.notNull(userId, "user Id is null");
        return roleRepo.selectRoleByUserId(userId);
    }

    @Override
    public Page list(RoleDTO sysRoleDTO) {
        SqlBuilder sqlBuilder = new SqlBuilder(Role.class)
                .eq("name", sysRoleDTO.getName())
                .eq("status", sysRoleDTO.getStatus())
                .orderByAsc("orderNum");

        Page page = Page.builder().build();
        if (sysRoleDTO.isEnablePage()) {
            page.setPageNum(sysRoleDTO.getPageNum());
            page.setPageSize(sysRoleDTO.getPageSize());
            sqlBuilder.page(page);
        }

        List<Role> list = roleRepo.select(sqlBuilder.build());

        page.setRows(list);

        return page;
    }

    @Override
    public String checkRoleCodeUnique(Role role) {
        Long id = null == role.getId() ? 0 : role.getId();
        role = roleRepo.selectOne(new SqlBuilder(Role.class).select("id")
                .eq("code", role.getCode())
                .build());
        if (null != role && !id.equals(role.getId())) {
            return GlobalConst.NOT_UNIQUE;
        }
        return GlobalConst.UNIQUE;
    }

    @Override
    public Page<User> selectAllocatedList(UserDTO userDTO) {
        Page page = Page.builder()
                .pageNum(userDTO.getPageNum())
                .pageSize(userDTO.getPageSize()).build();

        List<User> sysUsers = userRepo.selectAllocatedList(new SqlBuilder()
                .eq("roleId", userDTO.getRoleId())
                .eq("username", userDTO.getUsername())
                .eq("phone", userDTO.getPhone())
                .page(page)
                .build());

        page.setRows(sysUsers);

        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(RoleDTO sysRoleDTO) {

        Role sysRole = RoleFactory.newSysRole(sysRoleDTO);
        roleRepo.insert(sysRole);

        List<RoleMenu> roleMenus = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(sysRoleDTO.getMenuIds())) {
            Arrays.stream(sysRoleDTO.getMenuIds()).forEach(
                    item -> {
                        roleMenus.add(new RoleMenu(sysRole.getId(), Long.valueOf(item)));
                    }
            );
        }
        roleRepo.batchInsert(roleMenus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RoleDTO sysRoleDTO) {
        Role sysRole = RoleFactory.updateSysRole(sysRoleDTO);
        roleRepo.updateById(sysRole);

        List<RoleMenu> roleMenus = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(sysRoleDTO.getMenuIds())) {
            Arrays.stream(sysRoleDTO.getMenuIds()).forEach(
                    item -> {
                        roleMenus.add(new RoleMenu(sysRole.getId(), Long.valueOf(item)));
                    }
            );
        }

        roleRepo.deleteMenuByRoleId(sysRole.getId());

        roleRepo.batchInsert(roleMenus);

        menuRepo.refreshCache(sysRole.getCode());

    }


    @Override
    public Optional<Role> getById(String id) {
        SqlBuilder sqlBuilder = new SqlBuilder(Role.class)
                .eq("id", id);

        Role sysRole = roleRepo.selectOne(sqlBuilder.build());

        if (null == sysRole) {
            return Optional.empty();
        }
        return Optional.of(sysRole);
    }

    @Override
    public void deleteById(String... id) {
        roleRepo.deleteById(id);
    }

}
