package com.itacademy.database.dao;

import com.itacademy.database.entity.Department;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DepartmentDao implements BaseDao<Integer, Department> {

    private static final DepartmentDao INSTANCE = new DepartmentDao();

    public static DepartmentDao getInstance() {
        return INSTANCE;
    }
}
