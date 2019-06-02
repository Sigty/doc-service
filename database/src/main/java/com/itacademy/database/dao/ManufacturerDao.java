package com.itacademy.database.dao;

import com.itacademy.database.entity.Manufacturer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ManufacturerDao implements BaseDao<Integer, Manufacturer> {

    private static final ManufacturerDao INSTANCE = new ManufacturerDao();

    public static ManufacturerDao getInstance() {
        return INSTANCE;
    }

}
