package com.itacademy.database.converter;

import com.itacademy.database.config.DatabaseConfig;
import com.itacademy.database.dto.DocumentMidDto;
import com.itacademy.database.entity.Assembly;
import com.itacademy.database.entity.Document;
import com.itacademy.database.entity.User;
import com.itacademy.database.entity.UserSpecialty;
import java.time.OffsetDateTime;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseConfig.class)
public class DocumentToDocumentMidDtoTest {

    @Autowired
    DocumentToDocumentMidDto toDocumentMidDto;

    @Test
    public void convert() {
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

        DocumentMidDto documentMidDto = DocumentMidDto.builder()
                .id(1)
                .number("number")
                .createDocDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .lastName("last")
                .firstName("first")
                .docTypeName("AD")
                .build();

        DocumentMidDto convert = toDocumentMidDto.convert(document);
        assertEquals(convert, documentMidDto);
    }
}
