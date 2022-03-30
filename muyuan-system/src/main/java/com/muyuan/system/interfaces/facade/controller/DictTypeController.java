package com.muyuan.system.interfaces.facade.controller;

import com.muyuan.common.core.result.Result;
import com.muyuan.system.application.vo.DictTypeVO;
import com.muyuan.system.interfaces.dto.DictTypeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = {"字典类型接口"})
public interface DictTypeController {

    @GetMapping("/dictType")
    @ApiOperation(value = "字典类型查询")
    Result<List<DictTypeVO>> list(@ModelAttribute DictTypeDTO dictTypeDTO);

}
