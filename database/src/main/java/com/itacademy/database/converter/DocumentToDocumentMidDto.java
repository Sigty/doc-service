package com.itacademy.database.converter;

import com.itacademy.database.dto.DocumentMidDto;
import com.itacademy.database.entity.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentToDocumentMidDto implements Converter<DocumentMidDto, Document> {

    public DocumentMidDto convert(Document document) {
        return DocumentMidDto.builder()
                .id(document.getId())
                .number(document.getNumber())
                .docTypeName(document.getDocType().getName())
                .firstName(document.getDocUser().getUserSpecialty().getFirstName())
                .lastName(document.getDocUser().getUserSpecialty().getLastName())
                .createDocDate(document.getCreateDocDate())
                .version(document.getVersion())
                .build();
    }
}