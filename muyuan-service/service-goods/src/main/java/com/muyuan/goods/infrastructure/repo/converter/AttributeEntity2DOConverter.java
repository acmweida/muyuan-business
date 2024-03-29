package com.muyuan.goods.infrastructure.repo.converter;

import com.muyuan.goods.domains.model.entity.Attribute;
import com.muyuan.goods.infrastructure.repo.dataobject.AttributeDO;
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
public interface AttributeEntity2DOConverter {

    Attribute to(AttributeDO attributeDO);

    List<Attribute> to(List<AttributeDO> menuDOS);

    AttributeDO to(Attribute menuDO);

}
