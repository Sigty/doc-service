package com.itacademy.database.dao;

import com.itacademy.database.entity.Assembly;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssemblyDao implements BaseDao<Integer, Assembly> {

    private static final AssemblyDao INSTANCE = new AssemblyDao();

    public static AssemblyDao getInstance() {
        return INSTANCE;
    }
}
