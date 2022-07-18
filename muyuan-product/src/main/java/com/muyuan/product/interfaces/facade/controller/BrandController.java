package com.muyuan.product.interfaces.facade.controller;

import com.muyuan.common.core.constant.GlobalConst;
import com.muyuan.common.core.enums.ResponseCode;
import com.muyuan.common.core.result.Result;
import com.muyuan.common.core.result.ResultUtil;
import com.muyuan.common.log.annotion.Log;
import com.muyuan.common.log.enums.BusinessType;
import com.muyuan.common.mybatis.jdbc.page.Page;
import com.muyuan.common.web.annotations.RequirePermissions;
import com.muyuan.product.domains.dto.BrandDTO;
import com.muyuan.product.domains.model.Brand;
import com.muyuan.product.domains.service.BrandDomainService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * 品牌Controller
 *
 * @author ${author}
 * @date 2022-07-04T16:07:13.074+08:00
 */
@RestController
@RequestMapping("/brand")
@AllArgsConstructor
public class BrandController {

    private BrandDomainService brandDomainService;

    /**
     * 查询品牌列表
     */
    @ApiOperation("品牌分页查询")
    @RequirePermissions("product:brand:list")
    @GetMapping("/list")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "name", value = "品牌名称", dataTypeClass = String.class, paramType = "body"),
                    @ApiImplicitParam(name = "status", value = "状态 字典名称:product_brand_status", dataTypeClass = Long.class, paramType = "body"),
                    @ApiImplicitParam(name = "auditStatus", value = "审核状态:product_brand_audit_status", dataTypeClass = Integer.class, paramType = "body"),
                    @ApiImplicitParam(name = "pageSize", value = "", dataTypeClass = Integer.class, paramType = "body"),
                    @ApiImplicitParam(name = "pageNum", value = "", dataTypeClass = Integer.class, paramType = "body")
            }
    )
    public Result<Page> page(@ModelAttribute BrandDTO brandDTO) {
        Page<Brand> list = brandDomainService.page(brandDTO);
        return ResultUtil.success(list);
    }


    /**
     * 获取品牌详细信息
     */
    @RequirePermissions("product:brand:query")
    @GetMapping(value = "/{id}")
    public Result get(@PathVariable("id") Long id) {
        Optional<Brand> brand = brandDomainService.get(id);
        return brand.map(ResultUtil::success)
                .orElseGet(() -> ResultUtil.fail(ResponseCode.QUERY_NOT_EXIST));
    }

    /**
     * 新增品牌
     */
    @RequirePermissions("product:brand:add")
    @Log(title = "品牌", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("品牌新增")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "name", value = "品牌名称", dataTypeClass = String.class, paramType = "body"),
                    @ApiImplicitParam(name = "logo", value = "图标", dataTypeClass = Long.class, paramType = "body")
            }
    )
    public Result add(@RequestBody @Validated BrandDTO brandDTOb) {
        if (GlobalConst.NOT_UNIQUE.equals(
                brandDomainService.checkUnique(Brand.builder()
                        .name(brandDTOb.getName())
                        .build())
        )) {
            return ResultUtil.fail(ResponseCode.ADD_EXIST.getCode(), "品牌名称已存在");
        }

        brandDomainService.add(brandDTOb);
        return ResultUtil.success();
    }

    /**
     * 修改品牌
     */
    @RequirePermissions("product:brand:edit")
    @Log(title = "品牌", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("品牌信息修改")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id", value = "品牌ID", dataTypeClass = Long.class, paramType = "body",required = true),
                    @ApiImplicitParam(name = "name", value = "品牌名称", dataTypeClass = String.class, paramType = "body"),
                    @ApiImplicitParam(name = "englishName", value = "品牌英文名称", dataTypeClass = String.class, paramType = "body"),
                    @ApiImplicitParam(name = "logo", value = "图标", dataTypeClass = String.class, paramType = "body"),
                    @ApiImplicitParam(name = "status", value = "状态", dataTypeClass = Integer.class, paramType = "body"),
                    @ApiImplicitParam(name = "orderNum", value = "排序", dataTypeClass = Integer.class, paramType = "body")
            }
    )
    public Result edit(@RequestBody @Validated BrandDTO brandDTO) {
        if (ObjectUtils.isEmpty(brandDTO.getId())) {
            return ResultUtil.fail(ResponseCode.ARGUMENT_ERROR.getCode(),"品牌ID不能为空");
        }
        brandDomainService.update(brandDTO);
        return ResultUtil.success();
    }

    /**
     * 修改品牌
     */
    @RequirePermissions("product:brand:audit")
    @Log(title = "品牌", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    @ApiOperation("品牌审核")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id", value = "品牌ID", dataTypeClass = Long.class, paramType = "body",required = true),
                    @ApiImplicitParam(name = "auditStatus", value = "审核状态", dataTypeClass = Integer.class, paramType = "body",required = true)
            }
    )
    public Result audit(@RequestBody BrandDTO brandDTO) {
        if (ObjectUtils.isEmpty(brandDTO.getId())) {
            return ResultUtil.fail(ResponseCode.ARGUMENT_ERROR.getCode(),"品牌ID不能为空");
        }
        if (ObjectUtils.isEmpty(brandDTO.getAuditStatus())) {
            return ResultUtil.fail(ResponseCode.ARGUMENT_ERROR.getCode(),"认证状态不能为空");
        }

        brandDomainService.audit(brandDTO);
        return ResultUtil.success();
    }

    /**
     * 删除品牌
     */
    @RequirePermissions("product:brand:remove")
    @Log(title = "品牌", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long... ids) {
        brandDomainService.delete(ids);
        return ResultUtil.success();
    }

    /**
     * 修改品牌
     */
    @RequirePermissions("product:brand:linkCategory")
    @Log(title = "品牌", businessType = BusinessType.UPDATE)
    @PutMapping("/category")
    @ApiOperation("品牌关联分类")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id", value = "品牌ID", dataTypeClass = Long.class, paramType = "body",required = true),
                    @ApiImplicitParam(name = "categoryCodes", value = "分类Code列表", dataTypeClass = Long[].class, paramType = "body")
            }
    )
    public Result link(@RequestBody BrandDTO brandDTO) {
        if (ObjectUtils.isEmpty(brandDTO.getId())) {
            return ResultUtil.fail(ResponseCode.ARGUMENT_ERROR.getCode(),"品牌ID不能为空");
        }
        brandDomainService.linkCategory(brandDTO);
        return ResultUtil.success();
    }

    /**
     * 修改品牌
     */
    @RequirePermissions("product:brand:queryCategory")
    @Log(title = "品牌", businessType = BusinessType.UPDATE)
    @GetMapping("/category/{id}")
    @ApiOperation("品牌关联分类查询")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id", value = "品牌ID", dataTypeClass = Long.class, paramType = "path",required = true),
            }
    )
    public Result queryBrandCategory(@PathVariable Long id) {
        return ResultUtil.success(brandDomainService.getBrandCategory(id));
    }

}