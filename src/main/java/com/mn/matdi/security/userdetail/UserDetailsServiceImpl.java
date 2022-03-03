package com.mn.matdi.security.userdetail;

import com.mn.matdi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import com.mn.matdi.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.findByUserEmail(email)
                .orElseThrow(
                        () -> new NullPointerException("Can't find " + email)
                );

        return new UserDetailsImpl(user);
    }


}
