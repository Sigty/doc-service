package com.itacademy.database.entity;

import com.itacademy.database.config.TestConfig;
import com.itacademy.database.repository.ManufacturerRepository;
import com.itacademy.database.repository.PartRepository;
import com.itacademy.database.repository.UserRepository;
import com.itacademy.database.util.DatabaseHelper;
import java.time.OffsetDateTime;
import java.util.ArrayList;
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
public class PartTest {

    @Autowired
    private DatabaseHelper databaseHelper;

    @Before
    public void init() {
        databaseHelper.cleanDatabase();
    }

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    /**
     * Test get all part.
     */
    @Test
    public void getAllPartTest() {
        List<String> sort = partRepository.findAllSort();
        int expectedSize = 4;
        assertEquals(expectedSize, sort.size());
    }

    /**
     * Test get part by ID.
     */
    @Test
    public void findByIdPartTest() {
        Optional<Part> result = partRepository.findById(1);
        assertNotNull(result);
    }

    /**
     * Test save part.
     */
    @Test
    public void savePartTest() {
        Part part = Part.builder()
                .partNumber("partNumber")
                .description("description")
                .type("type")
                .sort("sort")
                .createPartDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .partUser(userRepository.findByLoginOrderByLogin("van"))
                .manufacturer(manufacturerRepository.findByName("murata").get())
                .build();
        partRepository.save(part);
        assertTrue(partRepository.findById(part.getId()).isPresent());
    }

    /**
     * Test delete part.
     */
    @Test
    public void saveAndDelPartTest() {

        Part part = Part.builder()
                .partNumber("partNumber2")
                .description("description")
                .type("type")
                .sort("sort")
                .createPartDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                .partUser(userRepository.findByLoginOrderByLogin("van"))
                .manufacturer(manufacturerRepository.findByName("murata").get())
                .build();
        partRepository.save(part);
        assertTrue(partRepository.findById(part.getId()).isPresent());
        partRepository.delete(part);
        int expectedSize = 21;
        Iterable<Part> partIterable = partRepository.findAll();
        List<Part> target = new ArrayList<>();
        partIterable.forEach(target::add);
        assertEquals(expectedSize, target.size());
    }


    /**
     * Test update part.
     */
//    @Test
//    public void updatePartTest() {
//
//        Part part = Part.builder()
//                .id(1)
//                .partNumber("testPartNumber")
//                .description("testDescription")
//                .type("type")
//                .sort("sort")
//                .createPartDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
//                .partUser(userRepository.findByLoginOrderByLogin("van"))
//                .manufacturer(manufacturerRepository.findByName("murata").get())
//                .build();
//        partRepository.update(part);
//        List<Part> parts = partDao.getAll();
//        int expectedSize = 3;
//        assertEquals(expectedSize, parts.size());
//        Optional<Part> result = partDao.get(1);
//        assertEquals("testPartNumber", result.get().getPartNumber());
//    }

}
