package com.itacademy.database.repository;

import com.itacademy.database.entity.Part;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends PagingAndSortingRepository<Part, Integer>, ExtendedPartRepository {

    @Query("select distinct p.sort from Part p")
    List<String> findAllSort();

    @Query("select distinct p.type from Part p")
    List<String> findAllType();
}