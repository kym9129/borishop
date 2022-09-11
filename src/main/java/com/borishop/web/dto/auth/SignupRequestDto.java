package com.borishop.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignupRequestDto {
    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 3, max = 50)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 4, max = 100)
    private String nickName;
}
