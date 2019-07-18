package com.itacademy.service.integration.service;

import com.itacademy.database.dto.DocumentMidDto;
import com.itacademy.service.config.ServiceConfig;
import com.itacademy.service.exception.ResponseException;
import com.itacademy.service.service.DocumentService;
import java.time.OffsetDateTime;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
@Transactional
@Rollback
public class DocumentServiceTest {

    @Autowired
    private DocumentService documentService;

    @Test
    public void deleteDocumentById() {

        Page<DocumentMidDto> allDoc = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        documentService.deleteDocumentById(35);
        Page<DocumentMidDto> allDocTarget = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        assertTrue(allDoc.getTotalElements() >= allDocTarget.getTotalElements());
    }

    @Test
    public void editDocumentById() {
        Page<DocumentMidDto> allDoc = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        DocumentMidDto documentMidDto = DocumentMidDto.builder()
                .number("number")
                .docTypeName("AD")
                .build();
        documentService.editDocumentById(36, documentMidDto);
        Page<DocumentMidDto> allDocTarget = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        assertTrue(allDoc.getTotalElements() == allDocTarget.getTotalElements());

        Page<DocumentMidDto> allDoc2 = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        DocumentMidDto documentMidDto2 = DocumentMidDto.builder()
                .number("number")
                .docTypeName("DT")
                .build();
        documentService.editDocumentById(36, documentMidDto2);
        Page<DocumentMidDto> allDocTarget2 = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        assertTrue(allDoc2.getTotalElements() == allDocTarget2.getTotalElements());

        Page<DocumentMidDto> allDoc3 = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        DocumentMidDto documentMidDto3 = DocumentMidDto.builder()
                .number("")
                .docTypeName("DT")
                .build();
        documentService.editDocumentById(36, documentMidDto3);
        Page<DocumentMidDto> allDocTarget3 = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        assertTrue(allDoc3.getTotalElements() == allDocTarget3.getTotalElements());

        Page<DocumentMidDto> allDoc4 = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        DocumentMidDto documentMidDto4 = DocumentMidDto.builder()
                .number("dt-12")
                .docTypeName("DT")
                .build();
        documentService.editDocumentById(36, documentMidDto4);
        Page<DocumentMidDto> allDocTarget4 = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        assertTrue(allDoc4.getTotalElements() == allDocTarget4.getTotalElements());

        Page<DocumentMidDto> allDoc5 = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        DocumentMidDto documentMidDto5 = DocumentMidDto.builder()
                .number("dt-12")
                .docTypeName("AD")
                .build();
        documentService.editDocumentById(36, documentMidDto5);
        Page<DocumentMidDto> allDocTarget5 = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        assertTrue(allDoc5.getTotalElements() == allDocTarget5.getTotalElements());
    }


    @Test
    public void saveDocumentText() {
        Page<DocumentMidDto> allDoc = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        DocumentMidDto documentMidDto = DocumentMidDto.builder()
                .number("number")
                .createDocDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .lastName("last")
                .firstName("first")
                .docTypeName("AD")
                .build();
        documentService.saveDocument(documentMidDto, "van");
        Page<DocumentMidDto> allDocTarget = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        assertTrue(allDoc.getTotalElements() < allDocTarget.getTotalElements());
    }

    @Test(expected = ResponseException.class)
    public void saveDocumentTextExp() {
        Page<DocumentMidDto> allDoc = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        DocumentMidDto documentMidDto = DocumentMidDto.builder()
                .number("ad-0000001")
                .createDocDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .lastName("last")
                .firstName("first")
                .docTypeName("AD")
                .build();
        documentService.saveDocument(documentMidDto, "van");
        Page<DocumentMidDto> allDocTarget = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        assertTrue(allDoc.getTotalElements() < allDocTarget.getTotalElements());
    }

    @Test
    public void findAllDocByOrderByDateDescTest() {
        Page<DocumentMidDto> allDoc = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        assertNotNull(allDoc);
    }

    @Test
    public void findAllDocByLoginUser() {
        Page<DocumentMidDto> allDoc = documentService.findAllDocByLoginUser("van", Pageable.unpaged());
        assertNotNull(allDoc);
    }
}