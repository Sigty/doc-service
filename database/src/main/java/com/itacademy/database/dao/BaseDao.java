package com.itacademy.database.dao;

import com.itacademy.database.entity.BaseEntity;
import com.itacademy.database.util.SessionManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;

public interface BaseDao<T extends Serializable, E extends BaseEntity<T>> {

    default T save(E entity) {
        try (Session session = SessionManager.getSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity.getId();
        }
    }

    default Optional<E> get(T id) {
        return Optional.ofNullable(SessionManager.getSession().get(getClazz(), id));
    }

    default void update(E entity) {
        try (Session session = SessionManager.getSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
    }

    default void delete(E entity) {
        try (Session session = SessionManager.getSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
    }

    default List<E> getAll() {
        try (Session session = SessionManager.getSession()) {
            Class<E> clazz = getClazz();
            return session.createQuery(String.format("select e from %s e", clazz.getSimpleName()), clazz).list();
        }
    }

    @SuppressWarnings("unchecked")
    default Class<E> getClazz() {
        Type entityType = ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1];
        return (Class<E>) entityType;
    }
}
