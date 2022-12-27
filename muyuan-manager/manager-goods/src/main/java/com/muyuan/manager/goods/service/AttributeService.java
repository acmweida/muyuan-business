package com.muyuan.manager.goods.service;

import com.muyuan.common.bean.Result;
import com.muyuan.goods.api.dto.AttributeRequest;
import com.muyuan.manager.goods.dto.AttributeParams;

/**
 * 商品分类属性Service接口
 * 
 * @author ${author}
 * @date 2022-06-23T14:17:01.512+08:00
 */
public interface AttributeService
{
    /**
     * 新增商品分类属性
     * 
     * @param params 商品分类属性
     * @return 结果
     */
    Result add(AttributeParams params);

    /**
     * 修改商品分类属性
     * 
     * @param attributeRequest 商品分类属性
     * @return 结果
     */
    Result update(AttributeRequest attributeRequest);

    /**
     * 批量删除商品分类属性
     * 
     * @param ids 需要删除的商品分类属性主键集合
     * @return 结果
     */
    Result delete(Long[] ids);

}
