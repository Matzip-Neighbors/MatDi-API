package com.mn.matdi.security;

import com.mn.matdi.dto.user.User;
import com.mn.matdi.mapper.KakaoUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final KakaoUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.findByUserEmail(email)
                .orElseThrow(
                        () -> new NullPointerException("Can't find " + email + " user")
                );
        return new UserDetailsImpl(user);
    }


}
