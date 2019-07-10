package com.itacademy.database.repository;

import com.itacademy.database.entity.Manufacturer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends PagingAndSortingRepository<Manufacturer, Integer> {

    @Query("select m.name from Manufacturer m")
    List<String> findManufacturerName();

    Optional<Manufacturer> findByName(String name);

    @Modifying
    @Query("update Manufacturer m set m.name = :name where m.id = :id")
    int updateNameManufacturer(@Param("id") Integer id, @Param("name") String name);
}