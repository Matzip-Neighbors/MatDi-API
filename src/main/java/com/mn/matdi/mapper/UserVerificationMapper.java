package com.mn.matdi.mapper;

import com.mn.matdi.dto.userVerification.UserVerification;
import com.mn.matdi.dto.userVerification.UserVerificationNumberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserVerificationMapper {

    // 유저인증 정보 입력
    void insertUserVerificationInfo(UserVerification.Info userVerificationInfoDto);

    // 유저인증 숫자 입력
    Optional<UserVerification.Response> checkUserVerificationNumber(UserVerificationNumberDto userVerificationNumberDto);

    void updateEmailStat(UserVerificationNumberDto userVerificationNumberDto);

    Integer emailDuplicationCheck(String email);

    boolean emailVerificationCodeCheck(String email);
}
