package com.itacademy.database.repository;

import com.itacademy.database.dto.FilterPartBasicDto;
import com.itacademy.database.dto.ViewPartBasicDto;
import java.util.List;

public interface ExtendedPartRepository {

    List<ViewPartBasicDto> filterListPart(FilterPartBasicDto filter);

    Long findCountPart(FilterPartBasicDto filter);
}