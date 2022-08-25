package com.sparta.blog.dto;

import com.sparta.blog.model.User;
import com.sparta.blog.model.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;


@Getter
public class UserInfoDto {
    public Long id;
    public String username;
    public String password;

    public UserInfoDto(){

    }

    public UserInfoDto(Optional<User> user) {
        this.id = user.get().getId();
        this.username = user.get().getUsername();
    }
}
