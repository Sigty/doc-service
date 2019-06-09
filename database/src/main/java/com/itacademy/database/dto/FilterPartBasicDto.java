package com.itacademy.database.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterPartBasicDto {

    private Integer page;
    private Integer recordsPerPage;
    private String partNumber;
    private String type;
    private String sort;
    private String manufacturer;
}
