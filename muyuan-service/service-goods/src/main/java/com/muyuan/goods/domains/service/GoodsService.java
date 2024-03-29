package com.muyuan.goods.domains.service;

import com.muyuan.common.bean.Page;
import com.muyuan.goods.domains.model.entity.Goods;
import com.muyuan.goods.face.dto.GoodsQueryCommand;

/**
 * @ClassName GoodsService 接口
 * Description GoodsService
 * @Author 2456910384
 * @Date 2022/8/26 14:03
 * @Version 1.0
 */
public interface GoodsService {

    Page<Goods> page(GoodsQueryCommand queryCommand);
}
