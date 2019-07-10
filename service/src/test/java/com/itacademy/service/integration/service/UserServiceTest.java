package com.itacademy.service.integration.service;

import com.itacademy.database.dto.RegistrationUserDto;
import com.itacademy.service.config.ServiceConfig;
import com.itacademy.service.exception.EntityNotFoundException;
import com.itacademy.service.exception.ResponseException;
import com.itacademy.service.service.UserService;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
@Transactional
@Rollback
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void findAllUserTest() {
        Page<RegistrationUserDto> allUser = userService.findAllUser(Pageable.unpaged());
        assertNotNull(allUser.getTotalElements());
    }

    @Test
    public void findUserByLoginTest() {
        RegistrationUserDto user = userService.findUserByLogin("van");
        assertNotNull(user);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findUserByLoginTestExc() {
        RegistrationUserDto user = userService.findUserByLogin("qpqqpwpqwe");
        assertNotNull(user);
    }

    @Test
    public void saveUserTest() {
        Page<RegistrationUserDto> allUserBase = userService.findAllUser(Pageable.unpaged());
        RegistrationUserDto userDto = RegistrationUserDto.builder()
                .login("qazwsx")
                .password("role")
                .email("asd@asd")
                .lastName("l")
                .firstName("f")
                .build();
        userService.saveUser(userDto);
        Page<RegistrationUserDto> allUserCurrent = userService.findAllUser(Pageable.unpaged());
        assertTrue(allUserBase.getTotalElements() <= allUserCurrent.getTotalElements());
    }

    @Test(expected = ResponseException.class)
    public void saveUserTestExp() {
        Page<RegistrationUserDto> allUserBase = userService.findAllUser(Pageable.unpaged());
        RegistrationUserDto userDto = RegistrationUserDto.builder()
                .login("van")
                .password("role")
                .email("asd@asd")
                .lastName("l")
                .firstName("f")
                .build();
        userService.saveUser(userDto);
        Page<RegistrationUserDto> allUserCurrent = userService.findAllUser(Pageable.unpaged());
        assertTrue(allUserBase.getTotalElements() < allUserCurrent.getTotalElements());
    }

    @Test(expected = ResponseException.class)
    public void saveUserTestExpEmail() {
        Page<RegistrationUserDto> allUserBase = userService.findAllUser(Pageable.unpaged());
        RegistrationUserDto userDto = RegistrationUserDto.builder()
                .login("vanTwo")
                .password("role")
                .email("ivanovi@gmail.com")
                .lastName("l")
                .firstName("f")
                .build();
        userService.saveUser(userDto);
        Page<RegistrationUserDto> allUserCurrent = userService.findAllUser(Pageable.unpaged());
        assertTrue(allUserBase.getTotalElements() < allUserCurrent.getTotalElements());
    }

    @Test
    public void changeRoleUserTest() {
        userService.changeRoleUser(1, "admin");
        userService.changeRoleUser(1, "user");
    }

    @Test(expected = Exception.class)
    public void changeRoleUserTestNull() {
        userService.changeRoleUser(1, null);
        userService.changeRoleUser(null, "user");
    }

    @Test
    public void changeDetailUserTest() {
        RegistrationUserDto userDto = RegistrationUserDto.builder()
                .login("gman")
                .lastName("Ivan")
                .firstName("Ivanov")
                .build();
        userService.changeDetailUser(userDto);

        RegistrationUserDto userDto2 = RegistrationUserDto.builder()
                .login("gman")
                .lastName("Ivan")
                .firstName("f")
                .build();
        userService.changeDetailUser(userDto2);

        RegistrationUserDto userDto3 = RegistrationUserDto.builder()
                .login("gman")
                .lastName("l")
                .firstName("Ivanov")
                .build();
        userService.changeDetailUser(userDto3);

        RegistrationUserDto userDto4 = RegistrationUserDto.builder()
                .login("gman")
                .lastName("l")
                .firstName("Ivanov")
                .build();
        userService.changeDetailUser(userDto4);

        RegistrationUserDto userDto5 = RegistrationUserDto.builder()
                .login("gman")
                .lastName("")
                .firstName("")
                .build();
        userService.changeDetailUser(userDto5);

        RegistrationUserDto userDto6 = RegistrationUserDto.builder()
                .login("gman")
                .lastName("")
                .firstName("Ivanov")
                .build();
        userService.changeDetailUser(userDto6);

        RegistrationUserDto userDto7 = RegistrationUserDto.builder()
                .login("gman")
                .lastName("Ivan")
                .firstName("")
                .build();
        userService.changeDetailUser(userDto7);
    }
}