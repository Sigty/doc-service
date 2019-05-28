package by.itacademy.database.dao;

import by.itacademy.database.entity.User;
import by.itacademy.database.util.SessionManager;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    public static UserDao getInstance() {
        return INSTANCE;
    }

    public List<User> findAllUser() {
        try (Session session = SessionManager.getSession()) {
            log.debug("begin transaction");
            session.beginTransaction();
            List<User> users = session.createQuery("select u from User u", User.class).list();
            session.getTransaction().commit();
            return users;
        }
    }
}
