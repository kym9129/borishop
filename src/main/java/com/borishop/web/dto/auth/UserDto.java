package com.borishop.web.dto.auth;

import com.borishop.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 3, max = 50)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String nickname;

    private String role;

    public static UserDto from(User user){
        if(user == null) return null;

        return UserDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRoleKey())
                .build();
    }
}
