package com.borishop.service;

import com.borishop.config.auth.jwt.JwtFilter;
import com.borishop.config.auth.jwt.TokenProvider;
import com.borishop.domain.user.Role;
import com.borishop.domain.user.User;
import com.borishop.domain.user.UserRepository;
import com.borishop.exception.DuplicateMemberException;
import com.borishop.exception.NotFoundMemberException;
import com.borishop.util.SecurityUtil;
import com.borishop.web.dto.auth.LoginRequestDto;
import com.borishop.web.dto.auth.TokenResponseDto;
import com.borishop.web.dto.auth.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TokenResponseDto login(LoginRequestDto loginDto){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // authenticate() 실행 시 CustomUserDetailsService에서 우리가 만들었던 loadUserByUsername()이 실행됨
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        return new TokenResponseDto(jwt);
    }

    @Transactional
    public UserDto signup(UserDto requestDto){
        if(userRepository.findByEmail(requestDto.getEmail()).orElse(null) != null){
            throw new DuplicateMemberException("이미 가입되어있는 유저입니다.");
        }

        User user = User.create(requestDto, passwordEncoder);
        return UserDto.from(userRepository.save(user));
    }

    /**
     * email에 해당하는 사용자 정보 조회
     * @param email
     * @return
     */
    @Transactional(readOnly = true)
    public UserDto getUser(String email){
        return UserDto.from(userRepository.findByEmail(email).orElse(null));
    }

    /**
     * 현재 SecurityContext에 저장되어있는 사용자 정보(=로그인한 사용자 정보)를 조회
     * @return
     */
    @Transactional(readOnly = true)
    public UserDto getMyUser(){
        String currentEmail = SecurityUtil.getCurrentUsername();
        User user = userRepository.findByEmail(currentEmail).orElseThrow(() -> new NotFoundMemberException("사용자 정보가 없습니다"));
        return UserDto.from(user);
    }
}
