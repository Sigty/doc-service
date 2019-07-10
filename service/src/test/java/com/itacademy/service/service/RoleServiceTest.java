package com.itacademy.service.service;

import com.itacademy.database.repository.RoleRepository;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RoleServiceTest {

    private RoleService roleService;
    private RoleRepository roleRepository;

    @Before
    public void init() {
        this.roleRepository = Mockito.mock(RoleRepository.class);
        this.roleService = new RoleService(roleRepository);
    }

    @Test
    public void findAllRoleNameTest() {
        List<String> allRoleList = Arrays.asList("admin", "user");
        Mockito.doReturn(allRoleList).when(roleRepository).findAllRoleName();
        List<String> allRoleService=roleService.findAllRoleName();
        assertEquals(allRoleService.size(), allRoleList.size());
    }
}