package com.muyuan.user.infrastructure.repo.mapper;

import com.muyuan.common.mybatis.jdbc.UserBaseMapper;
import com.muyuan.user.infrastructure.repo.dataobject.UserRoleDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * @ClassName RoleMapper
 * Description RoleMapper
 * @Author 2456910384
 * @Date 2021/12/24 11:21
 * @Version 1.0
 */
@Mapper
public interface UserRoleMapper extends UserBaseMapper<UserRoleDO> {

}
