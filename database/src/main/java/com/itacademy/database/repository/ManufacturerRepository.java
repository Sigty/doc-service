package com.itacademy.database.repository;

import com.itacademy.database.entity.Manufacturer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends PagingAndSortingRepository<Manufacturer, Integer> {

    @Query("select m.name from Manufacturer m")
    List<String> findManufacturerName();

    Optional<Manufacturer> findByName(String name);
}