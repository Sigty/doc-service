package com.itacademy.database.repository;

import com.itacademy.database.entity.DocPart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocPartRepository extends PagingAndSortingRepository<DocPart, Integer>, ExtendedPartRepository {

    Page<DocPart> findByDocumentId(Integer docId, Pageable pageable);

    Page<DocPart> findByPartsId(Integer docId, Pageable pageable);

    void deleteByDocumentIdAndPartsId(Integer docId, Integer partId);

    DocPart findByDocumentIdAndPartsIdOrderById(Integer docId, Integer partId);

    DocPart.Id findByDocumentIdAndPartsId(Integer docId, Integer partId);

}