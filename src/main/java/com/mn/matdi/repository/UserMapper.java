package com.mn.matdi.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    List<UserDto> getUserList();
}
