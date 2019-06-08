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

    Integer page;
    Integer recordsPerPage;
    String partNumber;
    String type;
    String sort;
    String manufacturer;
}
