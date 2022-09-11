package com.borishop.web.dto.auth;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;
}
