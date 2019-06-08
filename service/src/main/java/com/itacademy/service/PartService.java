package com.itacademy.service;

import com.itacademy.database.dao.PartDao;
import com.itacademy.database.dto.FilterPartBasicDto;
import com.itacademy.database.dto.ViewPartBasicDto;
import com.itacademy.database.entity.Part;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PartService {

    private static final PartService INSTANCE = new PartService();

    public static PartService getInstance() {
        return INSTANCE;
    }

    public List<Part> getAll() {
        log.info("findAllPart service<-dao");
        return PartDao.getInstance().getAll();
    }

    public List<ViewPartBasicDto> findListPart(FilterPartBasicDto filter) {
        log.info("findAllPart service<-dao");
        return PartDao.getInstance().findListPart(filter);
    }

    public List<ViewPartBasicDto> findListAllPart(FilterPartBasicDto filter) {
        return PartDao.getInstance().findAllListPart(filter);
    }
}
