package com.itacademy.database.repository;

import com.itacademy.database.entity.DocPart;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocPartRepository extends PagingAndSortingRepository<DocPart, Integer>, ExtendedPartRepository {
}