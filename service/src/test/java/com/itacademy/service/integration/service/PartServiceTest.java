package com.itacademy.service.integration.service;

import com.itacademy.database.dto.CreateOrEditPartDto;
import com.itacademy.database.dto.FilterPartBasicDto;
import com.itacademy.database.dto.PartBigDto;
import com.itacademy.database.dto.PartMidDto;
import com.itacademy.service.config.ServiceConfig;
import com.itacademy.service.service.PartService;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
@Rollback
@Transactional
public class PartServiceTest {

    @Autowired
    PartService partService;

    @Test
    public void findListPart() {
        FilterPartBasicDto dto = FilterPartBasicDto.builder()
                .partNumber("asdzxc")
                .type("c")
                .sort("c")
                .recordsPerPage(5)
                .page(1)
                .manufacturer("murata")
                .build();
        List<PartMidDto> listPart = partService.findListPart(dto);
        assertNotNull(listPart);
    }

    @Test
    public void findCountPart() {
        FilterPartBasicDto dto = FilterPartBasicDto.builder()
                .partNumber("asdzxc")
                .type("c")
                .sort("c")
                .recordsPerPage(5)
                .page(1)
                .manufacturer("murata")
                .build();
        Long countPart = partService.findCountPart(dto);
        assertNotNull(countPart);
    }

    @Test
    public void sortList() {
        List<String> strings = partService.sortList();
        assertTrue(strings.size() >= 0);
    }

    @Test
    public void typeList() {
        List<String> strings = partService.typeList();
        assertTrue(strings.size() >= 0);
    }

    @Test
    public void findAllByOrderByDateDesc() {
        Page<PartBigDto> bigDtos = partService.findAllByOrderByDateDesc(Pageable.unpaged());
        assertTrue(bigDtos.getTotalElements() >= 0);
    }

    @Test
    public void findAllByOrderByDateDescLogin() {
        Page<PartBigDto> partBigDtos = partService.findAllByOrderByDateDesc("gman", Pageable.unpaged());
        assertTrue(partBigDtos.getTotalElements() >= 0);
    }

    @Test
    public void deletePartById() {
        partService.deletePartById(1);
    }

    @Test
    public void deletePartByIdAndLogin() {
        partService.deletePartByIdAndLogin(1, "gman");
    }

    @Test
    public void findPartById() {
        CreateOrEditPartDto partDto = CreateOrEditPartDto.builder()
                .description("0.1 uF_10%_16V_X7R_0603")
                .manufacturer("murata")
                .partNumber("GRM188R71C104KA01D")
                .sort("c")
                .type("cap")
                .build();
        CreateOrEditPartDto partById = partService.findPartById(1);
        assertEquals(partDto, partById);
    }

    @Test
    public void editPartById() {
        CreateOrEditPartDto partDto = CreateOrEditPartDto.builder()
                .description("100 Ohm")
                .manufacturer("tdk")
                .partNumber("sfgsfdsgdf")
                .sort("r")
                .type("res")
                .build();

        partService.editPartById(1 ,partDto);
        assertEquals( partService.findPartById(1), partDto);
    }
}