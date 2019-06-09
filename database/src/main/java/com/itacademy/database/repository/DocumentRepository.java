package com.itacademy.database.repository;

import com.itacademy.database.entity.Document;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends PagingAndSortingRepository<Document, Integer>, ExtendedPartRepository {
}