package com.itacademy.database.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrEditPartDto {

    private String partNumber;
    private String description;
    private String type;
    private String sort;
    private String manufacturer;
}