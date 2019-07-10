package com.itacademy.service.integration.service;

import com.itacademy.service.config.ServiceConfig;
import com.itacademy.service.service.UserSecurityService;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
@Rollback
@Transactional
public class UserSecurityServiceTest {

    @Autowired
    private UserSecurityService userSecurityService;

    @Test
    public void loadUserByUsername() {
        UserDetails loadUser = userSecurityService.loadUserByUsername("van");
        assertNotNull(loadUser);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void loadUserByUsernameExc() {
        UserDetails loadUser = userSecurityService.loadUserByUsername("-1");
    }
}