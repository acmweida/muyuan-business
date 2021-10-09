package com.muyuan.member.interfaces.assembler;

import com.muyuan.member.domain.model.User;
import com.muyuan.member.domain.vo.UserVO;
import com.muyuan.member.interfaces.dto.UserDTO;
import org.springframework.beans.BeanUtils;

public class UserInfoAssembler {

    public static UserDTO buildUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return userDTO;
    }

    public static UserVO buildUserVO(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }
}