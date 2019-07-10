package com.itacademy.database.repository;

import com.itacademy.database.entity.UserSpecialty;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSpecialtyRepository extends PagingAndSortingRepository<UserSpecialty, Integer> {

    @Modifying
    @Query("update UserSpecialty u set u.firstName =:firsName where u.id = :id")
    int updateFirstNameUser(@Param("id") Integer id, @Param("firsName") String firsName);

    @Modifying
    @Query("update UserSpecialty u set u.lastName =:lastName where u.id = :id")
    int updateLastNameUser(@Param("id") Integer id, @Param("lastName") String lastName);

    @Modifying
    @Query("update UserSpecialty u set u.firstName =:firsName, u.lastName=:lastName where u.id = :id")
    int updateSpecialtyUser(@Param("id") Integer id, @Param("firsName") String firsName,
                            @Param("lastName") String lastName);
}