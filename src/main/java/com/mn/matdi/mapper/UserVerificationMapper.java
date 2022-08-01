package com.mn.matdi.mapper;

import com.mn.matdi.dto.userVerification.UserVerificationNumberDto;
import com.mn.matdi.dto.userVerification.UserVerificationResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserVerificationMapper {

    void insertUserVerificationInfo(UserVerificationResponseDto emailVerifyResponseDto);

    boolean checkEmailVerificationInfo(UserVerificationNumberDto request);

    void updateEmailStat(UserVerificationNumberDto request);

    Integer emailDuplicationCheck(String email);

    boolean emailVerificationCodeCheck(String email);
}
