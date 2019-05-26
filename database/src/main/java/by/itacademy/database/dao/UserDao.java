package by.itacademy.database.dao;

import by.itacademy.database.entity.User;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();
    private SessionFactory factory = new Configuration().configure().buildSessionFactory();

    public static UserDao getInstance() {
        return INSTANCE;
    }

    public List<User> findAllUser() {
        try (Session session = factory.openSession()) {
            log.debug("begin transaction");
            session.beginTransaction();
            log.info("try find all user");
            List<User> users = session.createQuery("select u from User u", User.class).list();
            session.getTransaction().commit();
            log.info("commit transaction");
            return users;
        }
    }
}