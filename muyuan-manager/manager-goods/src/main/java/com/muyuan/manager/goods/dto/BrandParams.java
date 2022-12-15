package com.muyuan.manager.goods.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 品牌对象 t_brand
 *
 * @author ${author}
 * @date 2022-07-04T14:16:24.789+08:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandParams {

    public interface Add {

    }

    public interface Update {

    }

    public interface Audit {

    }

    /**  */
    @NotNull(message = "品牌ID不能为空",groups = {Update.class,Audit.class})
    private Long id;

    @NotBlank(message = "品牌名称不能为空",groups = {Add.class,Update.class})
    /** 品牌名称 */
    private String name;

    @NotBlank(message = "图标不能为空",groups = {Add.class,Update.class})
    /** 图标 */
    private String logo;

    /** 排序 */
    private Integer orderNum = 0;

    /** 英文名称 */
    private String englishName;

    /** 备注 */
    private String remark;

    /** 审核状态  1-审核中  0-审核通过 2-审核魏通过 */
    @Range(message = "认证状态码输入错误",min = 0,max = 2,groups = {Audit.class})
    @NotNull(message = "认证状态不能为空",groups = {Audit.class})
    @ApiModelProperty(value = "认证状态:1-审核中  0-审核通过 2-审核魏通过",required = true)
    private Integer auditStatus;

    /** 状态  0-上架 1-下架 3-删除 4-禁用 */
    @Range(message = "状态码输入错误",min = 0,max = 4)
    private Integer status;

}
