package com.mn.matdi.mapper;

import com.mn.matdi.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
      Long insertUser(UserDto.Request request);
}