package com.mn.matdi.service;
import com.mn.matdi.dto.UserDto;
import com.mn.matdi.mapper.EmailVerify;
import com.mn.matdi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final EmailVerify emailVerify;

    public Long userSignup(UserDto.Request request) throws Exception {
        try {
            if (!emailVerify.emailVerificationCodeCheck(request.getEmail()))
                throw new Exception("이메일 인증이 안된 계정입니다.");
            else if (emailVerify.emailDuplicationCheck(request.getEmail()) != 0)
                throw new Exception("이메일 중복입니다.");
            else
                return userMapper.insertUser(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
