package com.borishop.domain.user;

import com.borishop.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users extends BaseTimeEntity {

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
    public Users(String email, String nickname, String password, Role role, boolean active) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public Users update(String nickname, String password, boolean active) {
        this.nickname = nickname;
        this.password = password;
        this.active = active;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
