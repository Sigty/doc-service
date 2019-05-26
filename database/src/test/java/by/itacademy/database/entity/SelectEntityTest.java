package by.itacademy.database.entity;

import by.itacademy.database.dao.UserDao;
import by.itacademy.database.util.UserTestDataImport;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class SelectEntityTest {

    private UserDao userDao = UserDao.getInstance();
    private SessionFactory sessionFactory;

    @Before
    public void initDb() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        UserTestDataImport.getInstance().importUserData(sessionFactory);
    }

    @After
    public void finish() {
        sessionFactory.close();
    }

    @Test
    public void getAllRole() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Role> query = session.createQuery("select e from Role e", Role.class);
            List<Role> roles = query.list();
            int expectedSize = 3;
            assertEquals(expectedSize, roles.size());
            session.getTransaction().commit();
        }
    }

    @Test
    public void getAllDepartment() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Department> query = session.createQuery("select d from Department d", Department.class);
            List<Department> departments = query.list();
            int expectedSize = 2;
            assertEquals(expectedSize, departments.size());
            session.getTransaction().commit();
        }
    }

    @Test
    public void getDetailUser() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<UserDetail> query = session.createQuery("select u from UserDetail u", UserDetail.class);
            List<UserDetail> userDetails = query.list();
            int expectedSize = 4;
            assertEquals(expectedSize, userDetails.size());
            session.getTransaction().commit();
        }
    }

    @Test
    public void getAllUser() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<User> result = userDao.findAllUser();
            int expectedSize = 4;
            assertEquals(expectedSize, result.size());
            session.getTransaction().commit();
        }
    }

    @Test
    public void getAllProject() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Project> query = session.createQuery("select e from Project e", Project.class);
            List<Project> result = query.list();
            int expectedSize = 3;
            assertEquals(expectedSize, result.size());
            session.getTransaction().commit();
        }
    }

    @Test
    public void getAllManufacturer() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Manufacturer> query = session.createQuery("select e from Manufacturer e", Manufacturer.class);
            List<Manufacturer> result = query.list();
            int expectedSize = 2;
            assertEquals(expectedSize, result.size());
            session.getTransaction().commit();
        }
    }

    @Test
    public void getAllPart() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Part> query = session.createQuery("select p from Part p", Part.class);
            List<Part> result = query.list();
            int expectedSize = 2;
            assertEquals(expectedSize, result.size());
            session.getTransaction().commit();
        }
    }

    @Test
    public void getAllDocType() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<DocType> query = session.createQuery("select d from DocType d", DocType.class);
            List<DocType> result = query.list();
            int expectedSize = 2;
            assertEquals(expectedSize, result.size());
            session.getTransaction().commit();
        }
    }

    @Test
    public void getAllDocument() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Document> query = session.createQuery("select d from Document d", Document.class);
            List<Document> result = query.list();
            int expectedSize = 2;
            assertEquals(expectedSize, result.size());
            session.getTransaction().commit();
        }
    }

    @Test
    public void getAllDocPart() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<DocPart> query = session.createQuery("select d from DocPart d", DocPart.class);
            List<DocPart> result = query.list();
            int expectedSize = 2;
            assertEquals(expectedSize, result.size());
            session.getTransaction().commit();
        }
    }
}

