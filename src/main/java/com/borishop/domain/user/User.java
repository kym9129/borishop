package com.borishop.domain.user;

import com.borishop.domain.BaseTimeEntity;
import com.borishop.web.dto.auth.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users") // mysql의 user테이블이 이미 있어 users로 변경
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email; // 아이디로 이메일 사용

    @Column(nullable = false)
    private String nickname; // 사용자 이름

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(columnDefinition = "boolean default true")
    private boolean active;

    @Builder
    public User(String email, String nickname, String password, Role role, boolean active) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public static User create(UserDto userDto, PasswordEncoder passwordEncoder){
        return User.builder()
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.USER)
                .active(true)
                .build();
    }

    public User update(String nickname, String password, boolean active) {
        this.nickname = nickname;
        this.password = password;
        this.active = active;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
