package com.itacademy.service.service;

import com.itacademy.database.repository.ManufacturerRepository;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ManufacturerServiceTest {

    private ManufacturerService manufacturerService;
    private ManufacturerRepository manufacturerRepository;

    @Before
    public void init() {
        this.manufacturerRepository = Mockito.mock(ManufacturerRepository.class);
        this.manufacturerService = new ManufacturerService(manufacturerRepository);
    }

    @Test
    public void manufacturersName() {
        List<String> listRep = Arrays.asList("murata", "tdk");
        Mockito.doReturn(listRep).when(manufacturerRepository).findManufacturerName();
        List<String> manufacturersName = manufacturerService.manufacturersName();
        assertEquals(manufacturersName.size(), listRep.size());
    }
}