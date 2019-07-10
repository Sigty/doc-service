package com.itacademy.database.converter;

public interface Converter<R, T> {

    R convert(T object);
}
