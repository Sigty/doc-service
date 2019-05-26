package by.itacademy.service;

import by.itacademy.database.dao.UserDao;
import by.itacademy.database.entity.User;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();

    public List<User> findAll() {
        log.info("findAllUser service<-dao");
        return UserDao.getInstance().findAllUser();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
