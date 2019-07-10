package com.itacademy.service.integration.service;

import com.itacademy.service.config.ServiceConfig;
import com.itacademy.service.service.ManufacturerService;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
@Rollback
@Transactional
public class ManufacturerServiceTest {

    @Autowired
    private ManufacturerService manufacturerService;

    @Test
    public void manufacturersName() {
        List<String> manufacturersName = manufacturerService.manufacturersName();
        assertNotNull(manufacturersName);
    }
}