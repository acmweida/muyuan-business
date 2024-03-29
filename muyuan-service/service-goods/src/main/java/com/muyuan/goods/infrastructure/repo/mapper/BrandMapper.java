package com.muyuan.goods.infrastructure.repo.mapper;

import com.muyuan.common.mybatis.jdbc.GoodsBaseMapper;
import com.muyuan.goods.infrastructure.repo.dataobject.BrandDO;

import java.util.List;

/**
 * 品牌Mapper接口
 * 
 * @author ${author}
 * @date 2022-07-04T14:16:24.789+08:00
 */
public interface BrandMapper extends GoodsBaseMapper<BrandDO> {

    List<BrandDO> selectByCategoryCode(Long... categoryCodes);

}
