package com.muyuan.member.domain.repo;

import com.muyuan.member.domain.model.User;

import java.util.List;
import java.util.Map;

public interface UserRepo {

    User find(int userNo);

    User selectOne(Map params);

    boolean insert(User dataObject);

    List<User> selectAllocatedList(Map params);
}
