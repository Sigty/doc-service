package com.itacademy.database.converter;

import com.itacademy.database.config.DatabaseConfig;
import com.itacademy.database.dto.PartMidDto;
import com.itacademy.database.entity.Manufacturer;
import com.itacademy.database.entity.Part;
import java.time.OffsetDateTime;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DatabaseConfig.class)
public class PartToPartMidDtoTest {

    @Autowired
    private PartToPartMidDto toPartMidDto;

    @Test
    public void convert() {
        Part part = Part.builder()
                .partNumber("partNumber")
                .description("description")
                .type("type")
                .sort("sort")
                .createPartDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .manufacturer(new Manufacturer("man"))
                .build();

        PartMidDto partDto = PartMidDto.builder()
                .partNumber("partNumber")
                .description("description")
                .type("type")
                .sort("sort")
                .createPartDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .manufacturer("man")
                .build();

        PartMidDto convert = toPartMidDto.convert(part);
        assertEquals(convert, partDto);
    }
}