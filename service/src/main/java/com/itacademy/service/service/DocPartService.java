package com.itacademy.service.service;

import com.itacademy.database.converter.DocPartToDocPartMidDto;
import com.itacademy.database.dto.DocPartMidDto;
import com.itacademy.database.entity.DocPart;
import com.itacademy.database.repository.DocPartRepository;
import com.itacademy.database.repository.DocTypeRepository;
import com.itacademy.database.repository.DocumentRepository;
import com.itacademy.database.repository.PartRepository;
import com.itacademy.service.exception.EntityNotFoundException;
import com.itacademy.service.exception.ResponseException;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@CacheConfig(cacheNames = "docPart")
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class DocPartService {

    private final DocumentRepository documentRepository;
    private final DocPartRepository docPartRepository;
    private final PartRepository partRepository;
    private final DocPartToDocPartMidDto toDocPartMidDto;
    private final DocTypeRepository docTypeRepository;

    @Cacheable(key = "{#root.methodName ,#root.args[0], #root.args[1]}")
    public Page<DocPartMidDto> findDocPartByDocumentId(Integer docId, Pageable pageable) {
        Page<DocPart> pageByDocumentId = docPartRepository.findByDocumentId(docId, pageable);
        return new PageImpl<>(pageByDocumentId
                .stream()
                .map(toDocPartMidDto::convert)
                .collect(Collectors.toList()), pageable, (int) pageByDocumentId.getTotalElements());
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void updateQuantityParts(Integer docId, Integer partId, Integer quantityPart) throws ResponseException {

        DocPart idOrderById = docPartRepository.findByDocumentIdAndPartsIdOrderById(docId, partId);
        idOrderById.setQuantityPart(quantityPart);
        try {
            docPartRepository.save(idOrderById);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseException();
        }
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void saveDocPartById(Integer docId, Integer partId, Integer quantityPart) throws ResponseException {

        DocPart docPart = DocPart.builder()
                .id(new DocPart.Id(docId, partId))
                .document(documentRepository.findById(docId)
                        .orElseThrow(() -> new EntityNotFoundException("Not found document by Id: (" + docId + ").")))
                .parts(partRepository.findById(partId)
                        .orElseThrow(() -> new EntityNotFoundException("Not found part by Id: (" + partId + ").")))
                .quantityPart(quantityPart)
                .build();
        try {
            docPartRepository.save(docPart);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseException();
        }
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void deleteDocPart(Integer docId, Integer partId) {
        try {
            docPartRepository.deleteByDocumentIdAndPartsId(docId, partId);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseException();
        }
    }

    @Cacheable
    public Set<String> docTypeSet() {
        return docTypeRepository.findAllDocType();
    }
}
