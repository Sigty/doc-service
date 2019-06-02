package com.itacademy.database.dao;

import com.itacademy.database.entity.DocPart;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DocPartDao implements BaseDao<DocPart.Id, DocPart> {

    private static final DocPartDao INSTANCE = new DocPartDao();

    public static DocPartDao getInstance() {
        return INSTANCE;
    }
}
