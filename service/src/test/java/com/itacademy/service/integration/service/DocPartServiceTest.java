package com.itacademy.service.integration.service;

import com.itacademy.service.config.ServiceConfig;
import com.itacademy.service.service.DocPartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
@Transactional
@Rollback
public class DocPartServiceTest {

    @Autowired
    private DocPartService docPartService;

    @Test
    public void saveDocPartByIdTest() {
        docPartService.saveDocPartById(1, 1, 100);
    }

}