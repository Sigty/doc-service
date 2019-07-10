package com.itacademy.database.converter;

import com.itacademy.database.dto.PartMidDto;
import com.itacademy.database.entity.Part;
import org.springframework.stereotype.Component;

@Component
public class PartToPartMidDto implements Converter<PartMidDto, Part> {

    public PartMidDto convert(Part part) {
        return PartMidDto.builder()
                .partNumber(part.getPartNumber())
                .description(part.getDescription())
                .type(part.getType())
                .sort(part.getSort())
                .createPartDate(part.getCreatePartDate())
                .manufacturer(part.getManufacturer().getName())
                .build();
    }
}