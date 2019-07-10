package com.itacademy.service.service;

import com.itacademy.database.dto.LoginDto;
import com.itacademy.database.repository.UserRepository;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.springframework.security.core.userdetails.User.builder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSecurityServiceTest {

    private UserSecurityService userSecurityService;
    private UserRepository userRepository;

    @Before
    public void init() {
        this.userRepository = Mockito.mock(UserRepository.class);
        this.userSecurityService = new UserSecurityService(userRepository);
    }

    @Test
    public void loadUserByUsername() {
        LoginDto dto = LoginDto.builder()
                .login("u")
                .password("p")
                .role("a")
                .build();

        UserDetails userDetails = builder()
                .username("u")
                .password("p")
                .authorities("a")
                .build();

        Mockito.doReturn(dto).when(userRepository).findByLogin("u");
        UserDetails loadUser = userSecurityService.loadUserByUsername("u");
        assertEquals(loadUser, userDetails);
    }
}