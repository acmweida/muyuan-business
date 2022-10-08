package com.muyuan.user.face.dto;

import com.muyuan.common.core.enums.PlatformType;
import lombok.Data;

/**
 * @ClassName OperatorQueryCommond
 * Description 运营人员信息查询参数
 * @Author 2456910384
 * @Date 2022/9/14 9:07
 * @Version 1.0
 */
@Data
public class UserQueryCommand {

    private String username;

    private PlatformType platformType;
}
