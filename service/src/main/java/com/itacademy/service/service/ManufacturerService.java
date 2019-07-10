package com.itacademy.service.service;

import com.itacademy.database.repository.ManufacturerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j
@CacheConfig(cacheNames = "sortTypes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional(readOnly = true)
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    @Cacheable(key = "#root.methodName")
    public List<String> manufacturersName() {
        return manufacturerRepository.findManufacturerName();
    }
}