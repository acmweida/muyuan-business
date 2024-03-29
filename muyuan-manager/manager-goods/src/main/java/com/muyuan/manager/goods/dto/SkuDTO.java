package com.muyuan.manager.goods.dto;

import com.muyuan.common.bean.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @ClassName GoodsDTO
 * Description 商品信息DTO
 * @Author 2456910384
 * @Date 2021/10/13 14:35
 * @Version 1.0
 */
@Data
public class SkuDTO extends PageDTO {

    private Long goodsId;

    /**
     * 售价
     */
    @ApiModelProperty(value = "售价")
    @NotNull(message = "SKU价格不能未空")
    private Double price;

    @ApiModelProperty(value = "图片")
    private String pic;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    @Min(value = 1,message = "库存数量必须大于0")
    @NotNull(message = "SKU库存不能未空")
    private Integer stock;

    @ApiModelProperty(value = "SKU详情")
    @NotBlank(message = "SKU内容不能为空")
    private String context;

}
