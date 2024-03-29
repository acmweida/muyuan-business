package com.muyuan.config.repo.converter;

import com.muyuan.config.entity.DictData;
import com.muyuan.config.entity.DictType;
import com.muyuan.config.repo.dataobject.DictDataDO;
import com.muyuan.config.repo.dataobject.DictTypeDO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @ClassName DictConverter 接口
 * Description
 * @Author 2456910384
 * @Date 2022/10/14 11:16
 * @Version 1.0
 */
@Mapper(componentModel = "spring")
public interface DictConverter {

    DictData to(DictDataDO dictDataDTO);

    List<DictData> to(List<DictDataDO> dictDataDTO);

    List<DictDataDO> toDO(List<DictData> dictDatas);

    DictType to(DictTypeDO dictTypeDTO);

    List<DictType> toType(List<DictTypeDO> dictDataDTO);

    DictTypeDO to(DictType dictType);

    DictDataDO to(DictData dictData);

}
