package com.itacademy.database.converter;

import com.itacademy.database.config.DatabaseConfig;
import com.itacademy.database.dto.RegistrationUserDto;
import com.itacademy.database.entity.Role;
import com.itacademy.database.entity.User;
import com.itacademy.database.entity.UserSpecialty;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseConfig.class)
public class UserToRegistrationUserDtoTest {

    @Autowired
    private UserToRegistrationUserDto toRegistrationUserDto;

    @Test
    public void convert() {
        User user = User.builder()
                .id(1)
                .login("login")
                .password("description")
                .userSpecialty(UserSpecialty.builder()
                        .lastName("l")
                        .firstName("f")
                        .email("e")
                        .build())
                .role(new Role("r"))
                .build();

        RegistrationUserDto userDto = RegistrationUserDto.builder()
                .id(1)
                .login("login")
                .password("description")
                .firstName("f")
                .lastName("l")
                .email("e")
                .role("r")
                .build();

        RegistrationUserDto convert = toRegistrationUserDto.convert(user);
        assertEquals(convert, userDto);
    }
}