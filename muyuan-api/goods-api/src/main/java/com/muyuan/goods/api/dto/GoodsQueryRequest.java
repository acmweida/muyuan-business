package com.muyuan.goods.api.dto;

import com.muyuan.common.bean.PageDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName GoodsQueryCommand
 * Description 商品查询 DTO
 * @Author 2456910384
 * @Date 2022/8/25 15:52
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class GoodsQueryRequest extends PageDTO {

    private static final long serialVersionUID = 1456932148569l;

    /**
     * 商品标题
     */
    private String name;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 分类id
     */
    private Long categoryCode;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 是否上架
     */
    private String status;

}
