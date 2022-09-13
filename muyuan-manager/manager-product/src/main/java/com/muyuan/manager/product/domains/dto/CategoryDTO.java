package com.muyuan.manager.product.domains.dto;

import com.muyuan.common.core.bean.BaseDTO;
import com.muyuan.manager.product.domains.model.Category;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName CategoryDTO
 * Description 类目 DTO
 * @Author 2456910384
 * @Date 2022/6/9 15:35
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CategoryDTO extends BaseDTO<CategoryDTO, Category> {

    @NotBlank(message = "分类名称不能为空")
    private String name;

    private String status;

    @NotBlank(message = "分类图标不能为空")
    private String logo;

    private Long parentId;

    private String code;

    private Integer orderNum;

    private Integer level;

    private String leaf;

    private Long id;

    private Long[] ids;

    private Long[] parentIds;

}