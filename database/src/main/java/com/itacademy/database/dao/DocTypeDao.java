package com.itacademy.database.dao;

import com.itacademy.database.entity.DocType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DocTypeDao implements BaseDao<Integer, DocType> {

    private static final DocTypeDao INSTANCE = new DocTypeDao();

    public static DocTypeDao getInstance() {
        return INSTANCE;
    }
}
