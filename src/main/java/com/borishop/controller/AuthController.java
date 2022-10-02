package com.borishop.controller;

import com.borishop.config.auth.jwt.JwtFilter;
import com.borishop.config.auth.jwt.TokenProvider;
import com.borishop.service.UserService;
import com.borishop.web.dto.auth.LoginRequestDto;
import com.borishop.web.dto.auth.UserDto;
import com.borishop.web.dto.auth.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginDto){
        TokenResponseDto tokenResponseDto = userService.login(loginDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenResponseDto.getToken());

        return new ResponseEntity<>(tokenResponseDto, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.signup(userDto));
    }

    /**
     * 로그인한 사용자의 정보 조회
     * @return
     */
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getMyUserInfo(){
        return ResponseEntity.ok(userService.getMyUser());
    }

    /**
     * 관리자가 특정 사용자의 정보 조회
     * @param email
     * @return
     */
    @GetMapping("/user/{email}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getUserInfo(@PathVariable String email){
        return ResponseEntity.ok(userService.getUser(email));
    }

}
