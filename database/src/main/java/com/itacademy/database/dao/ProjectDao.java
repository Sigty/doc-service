package com.itacademy.database.dao;

import com.itacademy.database.entity.Project;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectDao implements BaseDao<Integer, Project> {

    private static final ProjectDao INSTANCE = new ProjectDao();

    public static ProjectDao getInstance() {
        return INSTANCE;
    }
}
