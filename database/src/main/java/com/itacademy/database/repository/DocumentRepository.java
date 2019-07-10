package com.itacademy.database.repository;

import com.itacademy.database.entity.DocType;
import com.itacademy.database.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends PagingAndSortingRepository<Document, Integer>, ExtendedPartRepository {

    Page<Document> findAllByOrderByCreateDocDateDesc(Pageable pageable);

    Page<Document> findAllByDocUser_Login(String login, Pageable pageable);

    @Modifying
    @Query("update Document d set d.number =:number where d.id = :id")
    int updateNumberDocument(@Param("id") Integer id, @Param("number") String number);

    @Modifying
    @Query("update Document d set d.docType =:docType where d.id = :id")
    int updateDocTypeDocument(@Param("id") Integer id, @Param("docType") DocType docType);

    @Modifying
    @Query("update Document d set d.number =:number, d.docType=:docType where d.id = :id")
    int updateFullDocument(@Param("id") Integer id, @Param("number") String number,
                           @Param("docType") DocType docType);

}