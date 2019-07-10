package com.itacademy.database.repository;

import com.itacademy.database.entity.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer> {

    Role findRoleByRole(String role);

    @Query("select r.role from Role r")
    List<String> findAllRoleName();

    Optional<Role> findByRole(String role);
}