package com.mn.matdi.mapper;

import com.mn.matdi.dto.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
      Long insertUser(User.Request userRequest);
}