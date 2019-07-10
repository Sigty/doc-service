package com.itacademy.database.converter;

import com.itacademy.database.dto.PartBigDto;
import com.itacademy.database.entity.Part;
import org.springframework.stereotype.Component;

@Component
public class PartToPartBigDto implements Converter<PartBigDto, Part> {

    public PartBigDto convert(Part part) {
        return PartBigDto.builder()
                .id(part.getId())
                .partNumber(part.getPartNumber())
                .description(part.getDescription())
                .type(part.getType())
                .sort(part.getSort())
                .createPartDate(part.getCreatePartDate())
                .manufacturer(part.getManufacturer().getName())
                .build();
    }
}