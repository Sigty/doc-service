package com.itacademy.database.entity;

import com.itacademy.database.dao.PartDao;
import com.itacademy.database.util.SessionManager;
import static com.itacademy.database.util.SessionManager.getSession;
import com.itacademy.database.util.UserTestDataImport;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class PartTest {

    private static SessionFactory sessionFactory = SessionManager.getFactory();
    private final PartDao partDao = PartDao.getInstance();

    @BeforeClass
    public static void prepare() {
        UserTestDataImport.getInstance().importUserData(sessionFactory);
    }

    @AfterClass
    public static void clear() {
        sessionFactory.close();
    }

    /**
     * Test get all part.
     */
    @Test
    public void getAllPartTest() {
        List<Part> result = partDao.getAll();
        int expectedSize = 2;
        assertEquals(expectedSize, result.size());
    }

    /**
     * Test get part by ID.
     */
    @Test
    public void findByIdPartTest() {
        try (Session session = getSession()) {
            session.beginTransaction();
            Optional<Part> result = partDao.get(1);
            assertNotNull(result);
            session.getTransaction().commit();
        }
    }

    /**
     * Test save part.
     */
    @Test
    public void savePartTest() {
        try (Session session = getSession()) {
            Part part = Part.builder()
                    .partNumber("partNumber")
                    .description("description")
                    .type("type")
                    .sort("sort")
                    .createPartDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                    .partUser(session.get(User.class, 1))
                    .manufacturer(session.get(Manufacturer.class, 1))
                    .build();
            partDao.save(part);
            assertTrue(partDao.get(part.getId()).isPresent());
        }
    }

    /**
     * Test delete part.
     */
    @Test
    public void saveAndDelPartTest() {
        try (Session session = getSession()) {
            Part part = Part.builder()
                    .partNumber("partNumber2")
                    .description("description")
                    .type("type")
                    .sort("sort")
                    .createPartDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                    .partUser(session.get(User.class, 1))
                    .manufacturer(session.get(Manufacturer.class, 1))
                    .build();
            partDao.save(part);
            assertTrue(partDao.get(part.getId()).isPresent());
            partDao.delete(part);
            int expectedSize = 2;
            List<Part> result = partDao.getAll();
            assertEquals(expectedSize, result.size());
        }
    }

    /**
     * Test update part.
     */
    @Test
    public void updatePartTest() {
        try (Session session = getSession()) {
            Part part = Part.builder()
                    .id(1)
                    .partNumber("testPartNumber")
                    .description("testDescription")
                    .type("type")
                    .sort("sort")
                    .createPartDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                    .partUser(session.get(User.class, 1))
                    .manufacturer(session.get(Manufacturer.class, 1))
                    .build();
            partDao.update(part);
            List<Part> parts = partDao.getAll();
            int expectedSize = 3;
            assertEquals(expectedSize, parts.size());
            Optional<Part> result = partDao.get(1);
            assertEquals("testPartNumber", result.get().getPartNumber());
        }
    }
}
