package com.muyuan.goods.domains.model.aggregate;

import com.muyuan.common.core.domains.mode.aggregate.Aggregate;
import com.muyuan.goods.domains.model.entity.*;
import com.muyuan.goods.domains.model.valueobject.GoodsId;

import java.util.List;

/**
 * @ClassName GoodsAggregate
 * Description 商品聚会
 * @Author 2456910384
 * @Date 2022/8/24 17:17
 * @Version 1.0
 */
public class GoodsAggregate implements Aggregate<GoodsId> {

    private GoodsId id;

    private Goods goods;

    private List<Attribute> attributes;

    private List<AttributeValue> attributeValues;

    private List<Sku> skus;

    private Brand brand;

    @Override
    public GoodsId getId() {
        return id;
    }
}
