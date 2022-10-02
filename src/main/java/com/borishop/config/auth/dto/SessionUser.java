package com.borishop.config.auth.dto;

import com.borishop.domain.user.Users;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Users users){
        this.name = users.getNickname();
        this.email = users.getEmail();
        this.picture = users.getPassword();
    }
}
