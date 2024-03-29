package com.muyuan.user.domain.service;


import com.muyuan.common.bean.Page;
import com.muyuan.common.core.enums.PlatformType;
import com.muyuan.user.domain.model.entity.Operator;
import com.muyuan.user.domain.model.valueobject.RoleID;
import com.muyuan.user.domain.model.valueobject.UserID;
import com.muyuan.user.face.dto.OperatorCommand;
import com.muyuan.user.face.dto.UserQueryCommand;

import java.util.List;
import java.util.Optional;

/**
 * 用户域服务接口
 */
public interface OperatorService extends UserService<Operator> {

    /**
     * 角色分页查询
     * @param command
     * @return
     */
    Page<Operator> list(UserQueryCommand command);


    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    Optional<Operator> getOperatorByyId(UserID userId, PlatformType platformType);

    /**
     * 检查唯一性
     *
     * @param identify
     * @return
     */
    boolean exists(Operator.Identify identify);

    /**
     * 新增用户信息
     * @param command
     * @return
     */
    boolean addOperator(OperatorCommand command);

    /**
     * 列表查询
     * @param request
     * @return
     */
    Page<Operator> selectAllocatedList(UserQueryCommand request);

    /**
     * 列表查询 查询角色没有分配的用户
     * @param request
     * @return
     */
    Page<Operator> selectUnallocatedList(UserQueryCommand request);

    /**
     * 关联角色
     * @param userID
     * @param roleIDs
     * @return
     */
    boolean authRole(UserID userID, List<RoleID> roleIDs);

}
