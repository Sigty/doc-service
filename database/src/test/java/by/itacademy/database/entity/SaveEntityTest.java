package by.itacademy.database.entity;

import by.itacademy.database.util.SaveHelperTest;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

public class SaveEntityTest {

    SaveHelperTest saveHelp = SaveHelperTest.getInstance();

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
            User Alex = saveHelp.fullUser(session, "Alex", "pass", "Alex", "admin",
                    "QA", "mars", "venera");
            Serializable id = session.save(Alex);
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
            User Alex = saveHelp.fullUser(session, "Alex", "pass", "Alex", "admin",
                    "QA", "mars", "mercury");
            User Ka = saveHelp.fullUser(session, "b", "pass", "user", "user",
                    "Ka", "mars", "mercury");
            Set<User> mercuryUser = new HashSet<>();
            mercuryUser.add(Alex);
            mercuryUser.add(Ka);
            Project project = new Project("mercury", mercuryUser);
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
            User Ka = saveHelp.fullUser(session, "b", "pass", "user", "user",
                    "Ka", "mars", "mercury");
            Assembly pe = new Assembly("pe", true);
            Document document = Document.builder()
                    .number("1")
                    .createDocDate(OffsetDateTime.parse("2019-02-03T01:02:03Z"))
                    .docUser(Ka)
                    .docType(pe)
                    .build();
            session.save(document);
            session.save(pe);
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
            User Ka = saveHelp.fullUser(session, "b", "pass", "user", "user",
                    "Ka", "mars", "mercury");
            Part part = saveHelp.fullPart(session, "res1123", "resbest",
                    "res", "r", "2019-03-03T01:02:03Z", Ka, "koa");
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
            User ka = saveHelp.fullUser(session, "b", "pass", "user", "user",
                    "Ka", "mars", "venera");
            Part part = saveHelp.fullPart(session, "res1123", "resbest",
                    "res", "r", "2019-03-03T01:02:03Z", ka, "koa");
            Assembly pe = new Assembly("pe", true);
            Document document = Document.builder()
                    .number("1")
                    .createDocDate(OffsetDateTime.parse("2019-05-03T01:02:03Z"))
                    .docUser(ka)
                    .docType(pe)
                    .build();
            DocPart docPart = new DocPart(new DocPart.Id(1, 1), 3, document, part);
            session.save(pe);
            session.save(part);
            session.save(document);
            Serializable id = session.save(docPart);
            assertNotNull(id);
            session.getTransaction().commit();
        }
    }
}