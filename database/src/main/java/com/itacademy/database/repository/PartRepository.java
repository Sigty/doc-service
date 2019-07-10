package com.itacademy.database.repository;

import com.itacademy.database.entity.Manufacturer;
import com.itacademy.database.entity.Part;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends PagingAndSortingRepository<Part, Integer>, ExtendedPartRepository {

    @Cacheable(value = "sortTypes", key = "#root.methodName")
    @Query("select distinct p.sort from Part p")
    List<String> findAllSort();

    @Cacheable(value = "sortTypes", key = "#root.methodName")
    @Query("select distinct p.type from Part p")
    List<String> findAllType();

    Page<Part> findAllByOrderByCreatePartDateDesc(Pageable pageable);

    Page<Part> findAllByPartUser_LoginOrderByCreatePartDateDesc(String login, Pageable pageable);

    void deleteByIdAndPartUser_Login(Integer id, String login);

    @Modifying
    @Query("update Part p set p.partNumber = :partNumber where p.id = :id")
    int updatePartNumberPart(@Param("id") Integer id, @Param("partNumber") String partNumber);

    @Modifying
    @Query("update Part p set p.sort = :sort where p.id = :id")
    int updateSortPart(@Param("id") Integer id, @Param("sort") String sort);

    @Modifying
    @Query("update Part p set p.type = :type where p.id = :id")
    int updateTypePart(@Param("id") Integer id, @Param("type") String type);

    @Modifying
    @Query("update Part p set p.description = :description where p.id = :id")
    int updateDescriptionPart(@Param("id") Integer id, @Param("description") String description);

    @Modifying
    @Query("update Part p set p.manufacturer =:manufacturerId where p.id = :id")
    int updateManufactureIdPart(@Param("id") Integer id, @Param("manufacturerId") Manufacturer manufacturerId);

//    @Modifying
//    @Query("update Part p set p.manufacturer =:manufacturerId where p.id = :id")
//    int updateManufactureNamePart(@Param("id") Integer id, @Param("manufacturerId") String manufacturerId);
}