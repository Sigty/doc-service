package com.itacademy.database.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentMidDto {

    private Integer id;
    private String number;
    private String docTypeName;
    private String firstName;
    private String lastName;
    private OffsetDateTime createDocDate;
    private Long version;
}