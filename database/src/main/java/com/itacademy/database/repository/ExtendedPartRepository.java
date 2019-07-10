package com.itacademy.database.repository;

import com.itacademy.database.dto.CreateOrEditPartDto;
import com.itacademy.database.dto.FilterPartBasicDto;
import com.itacademy.database.dto.PartMidDto;
import java.util.List;
import java.util.Optional;

public interface ExtendedPartRepository {

    List<PartMidDto> filterListPart(FilterPartBasicDto filter);

    Long findCountPart(FilterPartBasicDto filter);

    Optional<CreateOrEditPartDto> findPartById(Integer id);
}