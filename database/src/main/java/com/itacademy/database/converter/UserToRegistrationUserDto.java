package com.itacademy.database.converter;

import com.itacademy.database.dto.RegistrationUserDto;
import com.itacademy.database.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserToRegistrationUserDto implements Converter<RegistrationUserDto, User> {

    public RegistrationUserDto convert(User user) {
        return RegistrationUserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .firstName(user.getUserSpecialty().getFirstName())
                .lastName(user.getUserSpecialty().getLastName())
                .email(user.getUserSpecialty().getEmail())
                .role(user.getRole().getRole())
                .build();
    }
}