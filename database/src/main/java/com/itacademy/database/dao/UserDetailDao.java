package com.itacademy.database.dao;

import com.itacademy.database.entity.UserDetail;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetailDao implements BaseDao<Integer, UserDetail> {

    private static final UserDetailDao INSTANCE = new UserDetailDao();

    public static UserDetailDao getInstance() {
        return INSTANCE;
    }
}
