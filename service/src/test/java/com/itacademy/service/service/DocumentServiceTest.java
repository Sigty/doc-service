package com.itacademy.service.service;

import com.itacademy.database.converter.DocumentToDocumentMidDto;
import com.itacademy.database.dto.DocumentMidDto;
import com.itacademy.database.entity.Assembly;
import com.itacademy.database.entity.DocType;
import com.itacademy.database.entity.Document;
import com.itacademy.database.entity.User;
import com.itacademy.database.entity.UserSpecialty;
import com.itacademy.database.repository.DocTypeRepository;
import com.itacademy.database.repository.DocumentRepository;
import com.itacademy.database.repository.UserRepository;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class DocumentServiceTest {

    private DocumentService documentService;
    private DocumentRepository documentRepository;
    private DocTypeRepository docTypeRepository;
    private UserRepository userRepository;
    private DocumentToDocumentMidDto toDocumentMidDto;

    @Before
    public void init() {
        this.documentRepository = Mockito.mock(DocumentRepository.class);
        this.docTypeRepository = Mockito.mock(DocTypeRepository.class);
        this.userRepository = Mockito.mock(UserRepository.class);
        this.toDocumentMidDto = Mockito.mock(DocumentToDocumentMidDto.class);
        this.documentService = new DocumentService(documentRepository, docTypeRepository,
                userRepository, toDocumentMidDto);
    }

    @Test
    public void findAllDocByLoginUser() {
        Document documentOne = Document.builder()
                .id(1)
                .number("number1")
                .createDocDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .docUser(User.builder().id(1).login("van").userSpecialty(UserSpecialty.builder()
                        .lastName("last")
                        .firstName("first")
                        .build()).build())
                .docType(new Assembly("AD", true))
                .build();

        Document documentTwo = Document.builder()
                .id(2)
                .number("number2")
                .createDocDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .docUser(User.builder().id(1).login("van").userSpecialty(UserSpecialty.builder()
                        .lastName("last")
                        .firstName("first")
                        .build()).build())
                .docType(new Assembly("AD", true))
                .build();
        List<Document> list = Arrays.asList(documentOne, documentTwo);
        Page<Document> page = new PageImpl<>(list, Pageable.unpaged(), list.size());
        Mockito.doReturn(page).when(documentRepository).findAllByDocUser_Login("van", Pageable.unpaged());
        Page<DocumentMidDto> docVan = documentService.findAllDocByLoginUser("van", Pageable.unpaged());
        assertEquals(page.getTotalElements(), docVan.getTotalElements());
    }

    @Test
    public void findAllDocByOrderByDateDesc() {
        Document documentOne = Document.builder()
                .id(1)
                .number("number1")
                .createDocDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .docUser(User.builder().id(1).login("van").userSpecialty(UserSpecialty.builder()
                        .lastName("last")
                        .firstName("first")
                        .build()).build())
                .docType(new Assembly("AD", true))
                .build();

        Document documentTwo = Document.builder()
                .id(2)
                .number("number2")
                .createDocDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .docUser(User.builder().id(1).login("van").userSpecialty(UserSpecialty.builder()
                        .lastName("last")
                        .firstName("first")
                        .build()).build())
                .docType(new Assembly("AD", true))
                .build();
        List<Document> list = Arrays.asList(documentOne, documentTwo);
        Page<Document> page = new PageImpl<>(list, Pageable.unpaged(), list.size());
        Mockito.doReturn(page).when(documentRepository).findAllByOrderByCreateDocDateDesc(Pageable.unpaged());
        Page<DocumentMidDto> docVan = documentService.findAllDocByOrderByDateDesc(Pageable.unpaged());
        assertEquals(page.getTotalElements(), docVan.getTotalElements());
    }

    @Test(expected = NullPointerException.class)
    public void saveDocument() {
        DocumentMidDto documentMidDto = DocumentMidDto.builder()
                .id(1)
                .number("number")
                .createDocDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .lastName("last")
                .firstName("first")
                .docTypeName("AD")
                .build();

        Document document = Document.builder()
                .id(1)
                .number("number")
                .createDocDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .docUser(User.builder().id(1).userSpecialty(UserSpecialty.builder()
                        .lastName("last")
                        .firstName("first")
                        .build()).build())
                .docType(new Assembly("AD", true))
                .build();

        Mockito.doReturn(User.builder().login("van").build())
                .when(userRepository).findByLoginOrderByLogin("van");
        Mockito.doReturn(documentMidDto).when(toDocumentMidDto).convert(document);
        documentService.saveDocument(documentMidDto, "van");
    }

    @Test
    public void findDocumentById() {

        Optional<Document> document = Optional.ofNullable(Document.builder()
                .id(1)
                .number("number")
                .createDocDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .docUser(User.builder().id(1).userSpecialty(UserSpecialty.builder()
                        .lastName("last")
                        .firstName("first")
                        .build()).build())
                .docType(new Assembly("AD", true))
                .build());

        DocumentMidDto documentMidDto = DocumentMidDto.builder()
                .id(1)
                .number("number")
                .createDocDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .lastName("last")
                .firstName("first")
                .docTypeName("AD")
                .build();

        Mockito.doReturn(document).when(documentRepository).findById(1);
        Mockito.doReturn(documentMidDto).when(toDocumentMidDto).convert(document.get());
        documentService.findDocumentById(1);
    }


    @Test(expected = NullPointerException.class)
    public void editDocumentById() {
        DocumentMidDto documentMidDto = DocumentMidDto.builder()
                .id(1)
                .number("number")
                .createDocDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .lastName("last")
                .firstName("first")
                .docTypeName("AD")
                .build();

        Optional<Document> document = Optional.ofNullable(Document.builder()
                .id(1)
                .number("number")
                .createDocDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .docUser(User.builder().id(1).userSpecialty(UserSpecialty.builder()
                        .lastName("last")
                        .firstName("first")
                        .build()).build())
                .docType(new Assembly("t", true))
                .build());

        Optional<DocType> byName = Optional.of(new Assembly("t", true));

        Mockito.doReturn(document).when(documentRepository).findById(1);
        Mockito.doReturn(documentMidDto).when(toDocumentMidDto).convert(document.get());
        documentService.findDocumentById(1);

        Mockito.doReturn(document).when(documentRepository).findById(1);
        Mockito.doReturn(documentMidDto).when(toDocumentMidDto).convert(document.get());

        Mockito.doReturn(byName).when(docTypeRepository).findByName("t");
        Mockito.doReturn(1).when(documentRepository)
                .updateFullDocument(1, "n", new Assembly("t", true));
        Mockito.doReturn(1).when(documentRepository)
                .updateNumberDocument(1, "n");
        Mockito.doReturn(1).when(documentRepository)
                .updateDocTypeDocument(1, new Assembly("t", true));

        documentService.editDocumentById(1, documentMidDto);
    }

    @Test
    public void deleteDocumentById() {
        Mockito.doNothing().when(documentRepository).deleteById(1);
        documentService.deleteDocumentById(1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void deleteDocumentByIdExp() {
        Mockito.doThrow(new DataIntegrityViolationException("1")).when(documentRepository).deleteById(999);
        documentService.deleteDocumentById(999);
    }
}