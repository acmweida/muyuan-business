package com.muyuan.store.system.domains.service.impl;

import com.muyuan.common.bean.Page;
import com.muyuan.common.mybatis.jdbc.SqlParamsBuilder;
import com.muyuan.store.system.domains.dto.RoleDTO;
import com.muyuan.store.system.domains.dto.UserDTO;
import com.muyuan.store.system.domains.factories.RoleFactory;
import com.muyuan.store.system.domains.model.Role;
import com.muyuan.store.system.domains.model.RoleMenu;
import com.muyuan.store.system.domains.model.User;
import com.muyuan.store.system.domains.repo.MenuRepo;
import com.muyuan.store.system.domains.repo.RoleRepo;
import com.muyuan.store.system.domains.repo.UserRepo;
import com.muyuan.store.system.domains.service.RoleDomainService;
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
    public Page page(RoleDTO roleDTO) {
        Page page = Page.builder()
                .pageNum(roleDTO.getPageNum())
                .pageSize(roleDTO.getPageSize())
                .build();

        List<Role> list = roleRepo.select(roleDTO,page);

        page.setRows(list);

        return page;
    }

    @Override
    public List<Role> list(RoleDTO roleDTO) {
        return roleRepo.select(roleDTO);
    }

    @Override
    public boolean checkRoleCodeUnique(Role role) {
        Long id = null == role.getId() ? 0 : role.getId();
        role = roleRepo.selectOne(role);
        if (null != role && !id.equals(role.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public Page<User> selectAllocatedList(UserDTO userDTO) {
        Page page = Page.builder()
                .pageNum(userDTO.getPageNum())
                .pageSize(userDTO.getPageSize()).build();

        List<User> sysUsers = userRepo.selectAllocatedList(new SqlParamsBuilder<User>()
                .param("roleId", userDTO.getRoleId())
                .param("username", userDTO.getUsername())
                .param("phone", userDTO.getPhone())
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
    public Optional<Role> getById(Long id) {
       return get(Role.builder().id(id).build());
    }

    @Override
    public Optional<Role> get(Role role) {
        return Optional.ofNullable(
                roleRepo.selectOne(role)
        );
    }

    @Override
    public void deleteById(String... id) {
        roleRepo.deleteById(id);
    }

}
