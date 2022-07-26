package com.mn.matdi.mapper;

import com.mn.matdi.dto.email.EmailVerificationNumberDto;
import com.mn.matdi.dto.email.EmailVerifyResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailVerify {

    void insertEmailVerificationInfo(EmailVerifyResponseDto emailVerifyResponseDto);

    boolean checkEmailVerificationInfo(EmailVerificationNumberDto request);

    void updateEmailStat(EmailVerificationNumberDto request);

    Integer emailDuplicationCheck(String email);

    boolean emailVerificationCodeCheck(String email);
}
