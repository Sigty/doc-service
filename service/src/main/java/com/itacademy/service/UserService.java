package com.itacademy.service;

import com.itacademy.database.dao.UserDao;
import com.itacademy.database.entity.User;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();

    public static UserService getInstance() {
        return INSTANCE;
    }

    public List<User> findAll() {
        log.info("findAllUser service<-dao");
        return UserDao.getInstance().getAll();
    }
}

