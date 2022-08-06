package com.mn.matdi.mapper;

import com.mn.matdi.dto.userVerification.UserVerificationNumberDto;
import com.mn.matdi.dto.userVerification.UserVerificationResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserVerificationMapper {

    // 유저인증 정보 입력
    void insertUserVerificationInfo(UserVerificationResponseDto emailVerifyResponseDto);

    // 유저인증 숫자 입력
    boolean checkUserVerificationNumber(UserVerificationNumberDto userVerificationNumberDto);

    void updateEmailStat(UserVerificationNumberDto userVerificationNumberDto);

    Integer emailDuplicationCheck(String email);

    boolean emailVerificationCodeCheck(String email);
}
