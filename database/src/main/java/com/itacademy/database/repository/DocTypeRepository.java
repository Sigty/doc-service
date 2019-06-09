package com.itacademy.database.repository;

import com.itacademy.database.entity.DocType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocTypeRepository extends PagingAndSortingRepository<DocType, Integer> {
}