package com.muyuan.manager.system.interfaces.assembler;

import com.muyuan.manager.system.domains.vo.DictDataVO;
import com.muyuan.manager.system.domains.model.DictData;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class DictDataAssembler {

    public static List<DictDataVO> buildDictDataVO(List<DictData> dictDatas) {
        List<DictDataVO> list = new ArrayList<>();
        for (DictData dictData : dictDatas) {
            if (null != dictData) {
                DictDataVO temp = new DictDataVO();
                BeanUtils.copyProperties(dictData, temp);
                list.add(temp);
            }
        }

        return list;
    }

    public static DictDataVO buildDictDataVO(DictData dictData) {
        if (null != dictData) {
            DictDataVO temp = new DictDataVO();
            BeanUtils.copyProperties(dictData, temp);
            return temp;
        }
        return null;
    }
}