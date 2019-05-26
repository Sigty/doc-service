package by.itacademy.database.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

@Log4j
public class SaveEntityTest {

    private SessionFactory sessionFactory;

    @Before
    public void initDb() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @After
    public void finish() {
        sessionFactory.close();
    }

    /**
     * Test insert role, department, userDetail, user.
     */
    @Test
    public void saveUser() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Role admin = new Role("admin");
            Department department = new Department("QA");
            UserDetail detailSveta = new UserDetail("Sveta", "Svetlgva", "aa@asd", department);
            User Sveta = new User("svaa", "avag", admin, detailSveta);
            session.save(admin);
            session.save(department);
            session.save(detailSveta);
            session.save(Sveta);
            Serializable id = session.save(Sveta);
            assertNotNull(id);
            session.getTransaction().commit();
        }
    }

    /**
     * Test insert project, Set<User>
     */
    @Test
    public void saveProject() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Role roleUser = new Role("user");
            Role manager = new Role("manager");
            Department QA = new Department("QA");
            Department DEV = new Department("DEV");
            UserDetail detailSveta = new UserDetail("Sveta", "Svtlova", "qe@e", DEV);
            UserDetail detailJoo = new UserDetail("Joo", "Ra", "ex@qwe", QA);
            User Sveta = new User("svet", "pass", roleUser, detailSveta);
            User Jo = new User("jojo", "qwe123", manager, detailJoo);
            Set<User> mercuryUser = new HashSet<>();
            mercuryUser.add(Sveta);
            mercuryUser.add(Jo);
            Project project = new Project("mercury", mercuryUser);
            session.save(roleUser);
            session.save(manager);
            session.save(QA);
            session.save(DEV);
            session.save(detailSveta);
            session.save(detailJoo);
            session.save(Sveta);
            session.save(Jo);
            session.save(project);
            Serializable id = session.save(project);
            assertNotNull(id);
            session.getTransaction().commit();
        }
    }

    /**
     * Test insert document, doc_type.
     */
    @Test
    public void saveDocument() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Role manager = new Role("manager");
            Department department = new Department("BA");
            UserDetail detailJo = new UserDetail("Jo", "Hi", "aa@asd", department);
            User jo = new User("svaa", "avag", manager, detailJo);
            ZonedDateTime createDocDate = ZonedDateTime.of(LocalDate.parse("2019-02-03"), LocalTime.parse("12:30:30"),
                    ZoneId.systemDefault());
            Assembly pe = new Assembly("pe", true);
            Document document = Document.builder()
                    .number("1")
                    .createDocDate(createDocDate)
                    .docUser(jo)
                    .docType(pe)
                    .build();
            session.save(document);
            session.save(pe);
            session.save(manager);
            session.save(department);
            session.save(detailJo);
            session.save(jo);
            Serializable id = session.save(document);
            assertNotNull(id);
            session.getTransaction().commit();
        }
    }

    /**
     * Test insert manufacturer, part.
     */
    @Test
    public void savePart() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ZonedDateTime dataFirst = ZonedDateTime.of(LocalDate.parse("2019-05-03"), LocalTime.parse("11:31:30"),
                    ZoneId.systemDefault());
            Role manager = new Role("manager");
            Department department = new Department("BA");
            UserDetail detailJo = new UserDetail("Jo", "Hi", "aa@asd", department);
            User jo = new User("svaa", "avag", manager, detailJo);
            Manufacturer manufacturer = new Manufacturer("vishay");
            Part part = Part.builder()
                    .partNumber("dt-7777777")
                    .description("detail")
                    .type("res")
                    .sort("r")
                    .createPartDate(dataFirst)
                    .partUser(jo)
                    .manufacturer(manufacturer)
                    .build();
            session.save(manager);
            session.save(department);
            session.save(detailJo);
            session.save(jo);
            session.save(manufacturer);
            session.save(part);
            Serializable id = session.save(part);
            assertNotNull(id);
            session.getTransaction().commit();
        }
    }

    /**
     * Test insert DocPart.
     */
    @Test
    public void saveDocPart() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ZonedDateTime dataFirst = ZonedDateTime.of(LocalDate.parse("2019-05-03"), LocalTime.parse("11:31:30"),
                    ZoneId.systemDefault());
            Role manager = new Role("manager");
            Department department = new Department("BA");
            UserDetail detailJo = new UserDetail("Jo", "Hi", "aa@asd", department);
            User jo = new User("svaa", "avag", manager, detailJo);
            Manufacturer manufacturer = new Manufacturer("vishay");
            Part part = Part.builder()
                    .partNumber("dt-7777777")
                    .description("detail")
                    .type("res")
                    .sort("r")
                    .createPartDate(dataFirst)
                    .partUser(jo)
                    .manufacturer(manufacturer)
                    .build();
            ZonedDateTime createDocDate = ZonedDateTime.of(LocalDate.parse("2019-02-03"), LocalTime.parse("12:30:30"),
                    ZoneId.systemDefault());
            Assembly pe = new Assembly("pe", true);
            Document document = Document.builder()
                    .number("1")
                    .createDocDate(createDocDate)
                    .docUser(jo)
                    .docType(pe)
                    .build();
            DocPart docPart = new DocPart(new DocPart.Id(1, 1), 3, document, part);
            session.save(manager);
            session.save(department);
            session.save(detailJo);
            session.save(jo);
            session.save(manufacturer);
            session.save(pe);
            session.save(part);
            session.save(document);
            Serializable id = session.save(docPart);
            assertNotNull(id);
            session.getTransaction().commit();
        }
    }

}