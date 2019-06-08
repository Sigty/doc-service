package com.itacademy.database.dao;

import com.itacademy.database.entity.Document;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentDao implements BaseDao<Integer, Document> {

    private static final DocumentDao INSTANCE = new DocumentDao();

    public static DocumentDao getInstance() {
        return INSTANCE;
    }
}
