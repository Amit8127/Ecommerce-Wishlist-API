package com.ecommerceSolutions.Transformers;

import com.ecommerceSolutions.Dtos.RequestDtos.CreateUserDto;
import com.ecommerceSolutions.Models.User;

public class UserTransformer {

    public static User userDtoToUser(CreateUserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .password(userDto.getPassword())
                .build();
    }
}
