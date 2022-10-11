package com.muyuan.user.dto.converter;

import com.muyuan.user.api.dto.UserDTO;
import com.muyuan.user.dto.UserVO;
import org.mapstruct.Mapper;

/**
 * @ClassName UserConverter 接口
 * Description UserConverter
 * @Author 2456910384
 * @Date 2022/10/11 14:46
 * @Version 1.0
 */
@Mapper
public interface UserConverter {

    UserVO toVO(UserDTO userDTO);
}