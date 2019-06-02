package com.itacademy.database.dao;

import com.itacademy.database.entity.Detail;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DetailDao implements BaseDao<Integer, Detail> {

    private static final DetailDao INSTANCE = new DetailDao();

    public static DetailDao getInstance() {
        return INSTANCE;
    }
}
