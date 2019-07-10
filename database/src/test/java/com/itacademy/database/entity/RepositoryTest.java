package com.itacademy.database.entity;

import com.itacademy.database.config.TestConfig;
import com.itacademy.database.dto.CreateOrEditPartDto;
import com.itacademy.database.dto.FilterPartBasicDto;
import com.itacademy.database.dto.LoginDto;
import com.itacademy.database.dto.PartMidDto;
import com.itacademy.database.repository.ManufacturerRepository;
import com.itacademy.database.repository.PartRepository;
import com.itacademy.database.repository.UserRepository;
import com.itacademy.database.util.DatabaseHelper;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
@Sql("classpath:doc-service_dml.sql")
public class RepositoryTest {

    @Autowired
    private DatabaseHelper databaseHelper;

    @Before
    public void init() {
        databaseHelper.cleanDatabase();
    }

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void getManufacturerTest() {
        Optional<Manufacturer> id = manufacturerRepository.findById(1);
        assertTrue(id.isPresent());
        Optional<Manufacturer> name = manufacturerRepository.findByName("murata");
        assertTrue(name.isPresent());
        assertEquals("murata", name.get().getName());
    }

    @Test
    public void getManufacturerTest2() {
        List<String> manufacturerName = manufacturerRepository.findManufacturerName();
        assertNotNull(manufacturerName.size());
    }


    @Test
    public void getAllPartTest() {
        List<String> sort = partRepository.findAllSort();
        int expectedSizeSort = 4;
        assertEquals(expectedSizeSort, sort.size());
        List<String> type = partRepository.findAllSort();
        int expectedSizeType = 4;
        assertEquals(expectedSizeType, type.size());
    }

    @Test
    public void filterListPartTest() {
        FilterPartBasicDto filter = FilterPartBasicDto.builder()
                .page(1)
                .recordsPerPage(10)
                .sort("ce")
                .build();
        List<PartMidDto> parts = partRepository.filterListPart(filter);
        int expectedSizeType = 1;
        assertEquals(expectedSizeType, parts.size());

        FilterPartBasicDto filter1 = FilterPartBasicDto.builder()
                .page(1)
                .recordsPerPage(10)
                .sort("c")
                .type("cap")
                .build();
        List<PartMidDto> parts1 = partRepository.filterListPart(filter1);
        int expectedSizeType1 = 8;
        assertEquals(expectedSizeType1, parts1.size());

        FilterPartBasicDto filter2 = FilterPartBasicDto.builder()
                .page(1)
                .recordsPerPage(10)
                .partNumber("GRM188R71C104KA01D")
                .sort("c")
                .type("cap")
                .build();
        List<PartMidDto> parts2 = partRepository.filterListPart(filter2);
        int expectedSizeType2 = 1;
        assertEquals(expectedSizeType2, parts2.size());
    }

    @Test
    public void findCountPartTest() {
        FilterPartBasicDto filter = FilterPartBasicDto.builder()
                .page(1)
                .recordsPerPage(10)
                .sort("c")
                .build();
        Long countParts = partRepository.findCountPart(filter);
        int expectedSizeType = 9;
        assertEquals(expectedSizeType, countParts.longValue());
    }

    @Test
    public void findByLoginTest() {
        LoginDto loginDTO = userRepository.findByLogin("van");
        String expectedSizeType = "van";
        assertEquals(expectedSizeType, loginDTO.getLogin());
    }

    @Test
    public void findPartByIdTest() {
        Optional<CreateOrEditPartDto> partById = partRepository.findPartById(1);
        assertNotNull(partById.isPresent());
    }
}