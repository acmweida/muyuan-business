package com.muyuan.manager.system.facade.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.muyuan.common.bean.Page;
import com.muyuan.common.bean.Result;
import com.muyuan.common.core.constant.GlobalConst;
import com.muyuan.common.core.enums.ResponseCode;
import com.muyuan.common.core.util.ExcelUtil;
import com.muyuan.common.core.util.ResultUtil;
import com.muyuan.common.web.annotations.RequirePermissions;
import com.muyuan.manager.system.dto.PermissionQueryParams;
import com.muyuan.manager.system.dto.RoleParams;
import com.muyuan.manager.system.dto.RoleQueryParams;
import com.muyuan.manager.system.dto.OperatorQueryParams;
import com.muyuan.manager.system.dto.assembler.SysRoleAssembler;
import com.muyuan.manager.system.dto.converter.PermissionConverter;
import com.muyuan.manager.system.dto.converter.RoleConverter;
import com.muyuan.manager.system.dto.vo.RoleVO;
import com.muyuan.manager.system.service.PermissionService;
import com.muyuan.manager.system.service.RoleService;
import com.muyuan.manager.system.service.OperatorService;
import com.muyuan.user.api.dto.PermissionDTO;
import com.muyuan.user.api.dto.RoleDTO;
import com.muyuan.user.api.dto.RoleRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName SysRoleController
 * Description 系统角色
 * @Author 2456910384
 * @Date 2022/4/24 14:54
 * @Version 1.0
 */
@RestController
@Api(tags = {"系统角色接口"})
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;

    private PermissionService permissionService;

    private RoleConverter converter;

    private PermissionConverter permissionConverter;

    private OperatorService operatorService;

    @GetMapping("/role/list")
    @ApiOperation(value = "角色列表查询")
    @RequirePermissions("system:role:query")
    public Result<Page<RoleVO>> list(@ModelAttribute RoleQueryParams params) {
        Page<RoleDTO> page = roleService.list(params);
        return ResultUtil.success(Page.copy(page, converter.toVO(page.getRows())));
    }

    @GetMapping("/role/{id}")
    @ApiOperation(value = "角色查询")
    @RequirePermissions("system:role:query")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id", value = "角色ID", dataTypeClass = Long.class, paramType = "path")}
    )
    public Result get(@PathVariable Long id) {
        if (ObjectUtils.isEmpty(id)) {
            return ResultUtil.fail(ResponseCode.QUERY_NOT_EXIST);
        }

        Optional<RoleDTO> dictData = roleService.getById(id);
        List<PermissionDTO> permissions = permissionService.getByRoleId(id);

        return dictData.map(dictDataDTO -> {
            RoleVO roleVO = converter.toVO(dictDataDTO);
            roleVO.setPermissions(permissionConverter.toVO(permissions));

            return   ResultUtil.success(roleVO);
        }).orElseGet(() -> ResultUtil.fail(ResponseCode.QUERY_NOT_EXIST));
    }

    @ApiOperation(value = "角色分配用户查询")
    @RequirePermissions("system:role:query")
    @GetMapping("/role/authUser/allocatedList")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "roleId", value = "角色ID", dataTypeClass = Long.class, paramType = "query", required = true),
                    @ApiImplicitParam(name = "username", value = "用户名", dataTypeClass = String.class, paramType = "query"),
                    @ApiImplicitParam(name = "phone", value = "手机号", dataTypeClass = String.class, paramType = "query")
            }
    )
    public Result allocatedList(@ModelAttribute OperatorQueryParams operatorQueryParams) {
        return ResultUtil.success(operatorService.selectAllocatedList(operatorQueryParams));
    }

    @ApiOperation(value = "角色为分配用户查询")
    @RequirePermissions("system:role:query")
    @GetMapping("/role/authUser/unallocatedList")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "roleId", value = "角色ID", dataTypeClass = Long.class, paramType = "query", required = true),
                    @ApiImplicitParam(name = "username", value = "用户名", dataTypeClass = String.class, paramType = "query"),
                    @ApiImplicitParam(name = "phone", value = "手机号", dataTypeClass = String.class, paramType = "query")
            }
    )
    public Result unallocatedList(@ModelAttribute OperatorQueryParams operatorQueryParams) {
        return ResultUtil.success(operatorService.selectUnallocatedList(operatorQueryParams));
    }

    @ApiOperation(value = "角色添加用户")
    @RequirePermissions("system:role:edit")
    @PutMapping("/role/authUser/selectAll")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "roleId", value = "角色ID", dataTypeClass = Long.class, paramType = "body", required = true),
                    @ApiImplicitParam(name = "userIds", value = "用户ID", dataType = "Long{]",dataTypeClass = Long.class,paramType = "body",required = true)
            }
    )
    @Valid
    public Result selectAll( @NotNull(message = "角色ID必填") Long roleId,
                             @NotBlank(message = "用户ID列表不能为空") Long[] userIds) {
        roleService.selectUser(roleId,userIds);
        return ResultUtil.success();
    }

    @PostMapping("/role")
    @ApiOperation(value = "角色添加")
    @ApiOperationSupport(ignoreParameters = "id")
    @RequirePermissions("system:role:add")
    public Result add(@RequestBody @Validated(RoleParams.Add.class) RoleParams roleParams) {
        RoleRequest request = converter.to(roleParams);
        if (ObjectUtils.isNotEmpty(roleParams.getMenuIds())) {
            Page<PermissionDTO> list = permissionService.list(PermissionQueryParams.builder()
                    .platformType(roleParams.getPlatformType())
                    .types(new String[]{GlobalConst.TYPE_DIR, GlobalConst.TYPE_MENU})
                    .build());

            List<PermissionDTO> permissionDTOS = list.getRows();
            Map<Long, Long> collect = permissionDTOS.stream().collect(Collectors.toMap(PermissionDTO::getResourceRef, PermissionDTO::getId));
            List<Long> permissionIDs = new ArrayList<>();
            for (Long menuId : roleParams.getMenuIds()) {
                permissionIDs.add(collect.get(menuId));
            }
            if (ObjectUtils.isNotEmpty(request.getPermissionIds())) {
                permissionIDs.addAll(Arrays.asList(request.getPermissionIds()));
            }
            request.setPermissionIds(permissionIDs.toArray(new Long[0]));
        }

        return roleService.add(request);
    }

    @PutMapping("/role")
    @ApiOperation(value = "角色添加")
    @RequirePermissions("system:role:edit")
    public Result update(@RequestBody @Validated(RoleParams.Update.class) RoleParams roleParams) {
        RoleRequest request = converter.to(roleParams);
        if (ObjectUtils.isNotEmpty(roleParams.getMenuIds())) {
            Page<PermissionDTO> list = permissionService.list(PermissionQueryParams.builder()
                    .platformType(roleParams.getPlatformType())
                    .types(new String[]{GlobalConst.TYPE_DIR, GlobalConst.TYPE_MENU})
                    .build());

            List<PermissionDTO> permissionDTOS = list.getRows();
            Map<Long, Long> collect = permissionDTOS.stream().collect(Collectors.toMap(PermissionDTO::getResourceRef, PermissionDTO::getId));
            List<Long> permissionIDs = new ArrayList<>();
            for (Long menuId : roleParams.getMenuIds()) {
                permissionIDs.add(collect.get(menuId));
            }
            if (ObjectUtils.isNotEmpty(request.getPermissionIds())) {
                permissionIDs.addAll(Arrays.asList(request.getPermissionIds()));
            }
            request.setPermissionIds(permissionIDs.toArray(new Long[0]));
        }

        return roleService.update(request);
    }

    @DeleteMapping("/role/{ids}")
    @ApiOperation(value = "角色删除")
    @RequirePermissions("system:role:remove")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id", value = "角色ID", dataType = "Long[]",dataTypeClass = Long.class, paramType = "path", required = true)}
    )
    public Result del(@PathVariable Long... ids) {
        if (ObjectUtils.isNotEmpty(ids)) {
            for (Long id : ids) {
                if (ObjectUtils.isEmpty(id)) {
                    return ResultUtil.fail(ResponseCode.ARGUMENT_ERROR);
                }
            }
        }

        return roleService.deleteById(ids);
    }

    @PostMapping("/role/export")
    @ApiOperation(value = "角色下载")
    @RequirePermissions("system:role:export")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "username", value = "用户名", dataTypeClass = String.class, paramType = "query"),
                    @ApiImplicitParam(name = "status", value = "状态 0-正常 1-禁用", dataTypeClass = String.class, paramType = "query")}
    )
    public void export(@ModelAttribute RoleQueryParams params, HttpServletResponse response) throws IOException {
        Page<RoleDTO> page = roleService.list(params);
        List<RoleDTO> rows = page.getRows();
        ExcelUtil.export(response, RoleVO.class, "角色信息", SysRoleAssembler.buildRoleVO(rows));
    }

}