package com.itacademy.database.repository;

import com.itacademy.database.entity.DocType;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocTypeRepository extends PagingAndSortingRepository<DocType, Integer> {

    @Query("select distinct d.name from DocType d order by d.name")
    Set<String> findAllDocType();

    Optional<DocType> findByName(String name);
}