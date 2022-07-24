package com.mn.matdi.mapper;

import com.mn.matdi.dto.UserSignUpRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
      Long insertUser(UserSignUpRequestDto userSignUpRequestDto);
}