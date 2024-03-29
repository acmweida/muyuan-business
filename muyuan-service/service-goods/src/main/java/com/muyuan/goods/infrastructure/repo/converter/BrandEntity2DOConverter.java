package com.muyuan.goods.infrastructure.repo.converter;

import com.muyuan.goods.domains.model.entity.Brand;
import com.muyuan.goods.infrastructure.repo.dataobject.BrandDO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @ClassName OperatorConverter
 * Description DO转换
 * @Author 2456910384
 * @Date 2022/9/14 10:38
 * @Version 1.0
 */
@Mapper(componentModel = "spring")
public interface BrandEntity2DOConverter {

    Brand to(BrandDO deptDO);

    List<Brand> to(List<BrandDO> menuDOS);

    BrandDO to(Brand menuDO);

}
