package com.mn.matdi.mapper;

import com.mn.matdi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<User> findByUserEmail(@Param("email") String email);
}
