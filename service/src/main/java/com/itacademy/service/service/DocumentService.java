package com.itacademy.service.service;

import com.itacademy.database.converter.DocumentToDocumentMidDto;
import com.itacademy.database.dto.DocumentMidDto;
import com.itacademy.database.entity.DocType;
import com.itacademy.database.entity.Document;
import com.itacademy.database.exception.DaoException;
import com.itacademy.database.repository.DocTypeRepository;
import com.itacademy.database.repository.DocumentRepository;
import com.itacademy.database.repository.UserRepository;
import static com.itacademy.database.util.PredicateUtil.predicateNoEqNoNullNoBlank;
import com.itacademy.service.exception.ResponseException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
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

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CacheConfig(cacheNames = "documents")
@Service
@Transactional(readOnly = true)
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocTypeRepository docTypeRepository;
    private final UserRepository userRepository;
    private final DocumentToDocumentMidDto toDocumentMidDto;

    @Cacheable
    public Page<DocumentMidDto> findAllDocByLoginUser(String login, Pageable pageable) {
        Page<Document> allDocument = documentRepository.findAllByDocUser_Login(login, pageable);
        return new PageImpl<>(allDocument
                .stream()
                .map(toDocumentMidDto::convert)
                .collect(Collectors.toList()), pageable, (int) allDocument.getTotalElements());
    }

    @Cacheable
    public Page<DocumentMidDto> findAllDocByOrderByDateDesc(Pageable pageable) {
        Page<Document> allDocument = documentRepository.findAllByOrderByCreateDocDateDesc(pageable);
        return new PageImpl<>(allDocument
                .stream()
                .map(toDocumentMidDto::convert)
                .collect(Collectors.toList()), pageable, (int) allDocument.getTotalElements());
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void saveDocument(DocumentMidDto documentMidDto, String loginUser) throws ResponseException {
        DocType docType;
        try {
            docType = docTypeRepository.findByName(documentMidDto.getDocTypeName())
                    .orElseThrow(DaoException::new);
        } catch (DaoException ex) {
            throw new ResponseException("Not find");
        }
        Document document = Document.builder()
                .number(documentMidDto.getNumber())
                .docType(docType)
                .docUser(userRepository.findByLoginOrderByLogin(loginUser))
                .createDocDate(OffsetDateTime.now(ZoneId.systemDefault()))
                .build();
        try {
            documentRepository.save(document);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseException("Document dont save: " + "(" + document.getNumber() + ")");
        }
    }

    @Cacheable
    public DocumentMidDto findDocumentById(Integer id) {
        return Optional.of(id)
                .map(id1 -> documentRepository.findById(id1).get())
                .map(toDocumentMidDto::convert)
                .orElseThrow(() -> new ResponseException("Document: (" + id + ") not found"));
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void editDocumentById(Integer id, DocumentMidDto documentNew) throws ResponseException {
        DocumentMidDto documentDefault = findDocumentById(id);
        String defaultNumber = documentDefault.getNumber();
        String defaultDocType = documentDefault.getDocTypeName();
        DocType docType;
        try {
            docType = docTypeRepository.findByName(documentNew.getDocTypeName()).orElseThrow(DaoException::new);
        } catch (DaoException ex) {
            throw new ResponseException("Doctype: (" + documentNew.getDocTypeName() + ") not found.");
        }
        if (predicateNoEqNoNullNoBlank(documentNew.getNumber(), defaultNumber)
                && predicateNoEqNoNullNoBlank(documentNew.getDocTypeName(), defaultDocType)) {
            try {
                documentRepository.updateFullDocument(id, documentNew.getNumber(), docType);
            } catch (DataIntegrityViolationException ex) {
                new ResponseException(documentNew.getNumber());
            }
        } else if (predicateNoEqNoNullNoBlank(documentNew.getNumber(), defaultNumber)) {
            try {
                documentRepository.updateNumberDocument(id, documentNew.getNumber());
            } catch (DataIntegrityViolationException ex) {
                new ResponseException(documentNew.getNumber());
            }
        } else if (predicateNoEqNoNullNoBlank(documentNew.getDocTypeName(), defaultDocType)) {
            documentRepository.updateDocTypeDocument(id, docType);
        }
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void deleteDocumentById(Integer id) {
        documentRepository.deleteById(id);
    }
}