package com.mn.matdi.mapper;

import com.mn.matdi.dto.EmailRequestDto;
import com.mn.matdi.dto.EmailVerificationNumberDto;
import com.mn.matdi.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
      Long insertUser(UserDto.Request request);
      void insertEmailVerificationInfo(EmailRequestDto.request request);
      boolean checkEmailVerificationInfo(EmailVerificationNumberDto request);
      void updateEmailStat(EmailVerificationNumberDto request);
      Integer emailDuplicationCheck(String email);
      boolean emailVerificationCodeCheck(String email);
}