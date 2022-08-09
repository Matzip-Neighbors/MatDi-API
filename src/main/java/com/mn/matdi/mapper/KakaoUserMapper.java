package com.mn.matdi.mapper;

import com.mn.matdi.dto.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface KakaoUserMapper {

    @Select("SELECT * FROM user WHERE email = #{email}")
    Optional<User.Info> findByUserEmail(@Param("email") String email);

    void insertKakaoUser(User.Info user);
}
