package com.itacademy.database.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartBigDto {

    private Integer id;
    private String partNumber;
    private String description;
    private String type;
    private String sort;
    private OffsetDateTime createPartDate;
    private String manufacturer;
}
