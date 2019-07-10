package com.itacademy.database.repository;

import com.itacademy.database.entity.Role;
import com.itacademy.database.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer>, ExtendedUserRepository {

    User findByLoginOrderByLogin(String loginUser);

    Page<User> findAllByOrderById(Pageable pageable);

    @Modifying
    @Query("update User u set u.role =:roleUser where u.id = :id")
    int updateRoleUser(@Param("id") Integer id, @Param("roleUser") Role roleUser);
}