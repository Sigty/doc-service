package com.itacademy.service.service;

import com.itacademy.database.converter.PartToPartBigDto;
import com.itacademy.database.dto.CreateOrEditPartDto;
import com.itacademy.database.repository.ManufacturerRepository;
import com.itacademy.database.repository.PartRepository;
import com.itacademy.database.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PartServiceTest {

    private PartService partService;
    private PartRepository partRepository;
    private ManufacturerRepository manufacturerRepository;
    private UserRepository userRepository;
    private PartToPartBigDto partMidDto;

    @Before
    public void init() {
        this.partRepository = Mockito.mock(PartRepository.class);
        this.manufacturerRepository = Mockito.mock(ManufacturerRepository.class);
        this.userRepository = Mockito.mock(UserRepository.class);
        this.partMidDto = Mockito.mock(PartToPartBigDto.class);
        this.partService = new PartService(partRepository, manufacturerRepository, userRepository, partMidDto);
    }

    @Test
    public void findListPart() {
    }

    @Test
    public void findCountPart() {
    }

    @Test
    public void sortList() {
        List<String> sortList = Arrays.asList("a", "b", "c");
        Mockito.doReturn(sortList).when(partRepository).findAllSort();
        List<String> result = partService.sortList();
        assertEquals(sortList.size(), result.size());
    }

    @Test
    public void typeList() {
        List<String> typeList = Arrays.asList("a", "b", "c");
        Mockito.doReturn(typeList).when(partRepository).findAllType();
        List<String> result = partService.typeList();
        assertEquals(typeList.size(), result.size());
    }

    @Test
    public void findAllByOrderByDateDesc() {
    }

    @Test
    public void findAllByOrderByDateDesc1() {
    }

    @Test
    public void deletePartById() {
        Mockito.doNothing().when(partRepository).deleteById(1);
        partService.deletePartById(1);
    }

    @Test
    public void deletePartByIdAndLogin() {
        Mockito.doNothing().when(partRepository).deleteByIdAndPartUser_Login(1, "a");
        partService.deletePartByIdAndLogin(1, "a");
    }

    @Test
    public void findPartById() {
        Mockito.doReturn(Optional.of(CreateOrEditPartDto.builder().partNumber("1")
                .build())).when(partRepository).findPartById(1);
        CreateOrEditPartDto partById = partService.findPartById(1);
        assertNotNull(partById);
    }

    @Test
//    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void savePart(){}

}