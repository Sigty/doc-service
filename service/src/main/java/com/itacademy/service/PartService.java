package com.itacademy.service;

import com.itacademy.database.dto.FilterPartBasicDto;
import com.itacademy.database.dto.ViewPartBasicDto;
import com.itacademy.database.repository.PartRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional
public class PartService {

    @Autowired
    private final PartRepository partRepository;

    public List<ViewPartBasicDto> findListPart(FilterPartBasicDto filter) {
        log.info("findByFilter service<-dao");
        return partRepository.filterListPart(filter);
    }

    public Long findCountPart(FilterPartBasicDto filter) {
        log.info("findCount service<-dao");
        return partRepository.findCountPart(filter);
    }

    public List<String> sortList() {
        log.info("findAllSort service<-dao");
        return partRepository.findAllSort();
    }

    public List<String> typeList() {
        log.info("findAllType service<-dao");
        return partRepository.findAllType();
    }
}