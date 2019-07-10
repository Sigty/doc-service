package com.itacademy.database.converter;

import com.itacademy.database.dto.DocPartMidDto;
import com.itacademy.database.entity.DocPart;
import org.springframework.stereotype.Component;

@Component
public class DocPartToDocPartMidDto implements Converter<DocPartMidDto, DocPart> {

    public DocPartMidDto convert(DocPart docPart) {
        return DocPartMidDto.builder()
                .docId(docPart.getDocument().getId())
                .partId(docPart.getParts().getId())
                .partNumber(docPart.getParts().getPartNumber())
                .description(docPart.getParts().getDescription())
                .type(docPart.getParts().getType())
                .sort(docPart.getParts().getSort())
                .createPartDate(docPart.getParts().getCreatePartDate())
                .manufacturer(docPart.getParts().getManufacturer().getName())
                .quantityPart(docPart.getQuantityPart())
                .version(docPart.getVersion())
                .build();
    }
}