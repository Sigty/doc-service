package com.itacademy.database.converter;

import com.itacademy.database.config.DatabaseConfig;
import com.itacademy.database.dto.DocPartMidDto;
import com.itacademy.database.entity.Assembly;
import com.itacademy.database.entity.DocPart;
import com.itacademy.database.entity.Document;
import com.itacademy.database.entity.Manufacturer;
import com.itacademy.database.entity.Part;
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
public class DocPartToDocPartMidDtoTest {

    @Autowired
    private DocPartToDocPartMidDto toDocPartMidDto;

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
                .version(0L)
                .build();

        Part part = Part.builder()
                .id(1)
                .partNumber("partNumber")
                .description("description")
                .type("type")
                .sort("sort")
                .createPartDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .manufacturer(new Manufacturer("man"))
                .build();


        DocPart docPart = DocPart.builder()
                .id(new DocPart.Id(document.getId(), part.getId()))
                .document(document)
                .parts(part)
                .quantityPart(100)
                .version(0L)
                .build();

        DocPartMidDto docPartMidDto = DocPartMidDto.builder()
                .docId(1)
                .partId(1)
                .partNumber("partNumber")
                .description("description")
                .type("type")
                .sort("sort")
                .createPartDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .manufacturer("man")
                .quantityPart(100)
                .version(0L)
                .build();

        DocPartMidDto convert = toDocPartMidDto.convert(docPart);
        assertEquals(convert, docPartMidDto);
    }
}