package com.muyuan.goods.infrastructure.repo.converter;

import com.muyuan.goods.domains.model.entity.Feature;
import com.muyuan.goods.infrastructure.repo.dataobject.FeatureDO;
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
public interface FeatureEntity2DOConverter {

    Feature to(FeatureDO featureDO);

    List<Feature> to(List<FeatureDO> featureDOS);

    FeatureDO to(Feature feature);

}
