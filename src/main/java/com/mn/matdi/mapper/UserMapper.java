package com.mn.matdi.mapper;

import com.mn.matdi.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE email = #{email}")
    Optional<User> findByUserEmail(@Param("email") String email);

    void insertKakaoUser(User user);
}
