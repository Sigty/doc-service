package com.itacademy.database.repository;

import com.itacademy.database.entity.UserDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends PagingAndSortingRepository<UserDetail, Integer> {
}