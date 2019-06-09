package com.itacademy.service;

import com.itacademy.database.repository.ManufacturerRepository;
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
public class ManufacturerService {

    @Autowired
    private final ManufacturerRepository manufacturerRepository;

    public List<String> manufacturersName() {
        log.info("findAllManufacturer service<-dao");
        return manufacturerRepository.findManufacturerName();
    }
}