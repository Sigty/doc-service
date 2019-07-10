package com.itacademy.database.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHelper {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public DatabaseHelper(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void cleanDatabase() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from DocPart ").executeUpdate();
        entityManager.createQuery("delete from DocType ").executeUpdate();
        entityManager.createQuery("delete from Document ").executeUpdate();
        entityManager.createQuery("delete from Manufacturer ").executeUpdate();
        entityManager.createQuery("delete from Part").executeUpdate();
        entityManager.createQuery("delete from Role").executeUpdate();
        entityManager.createQuery("delete from User").executeUpdate();
        entityManager.createQuery("delete from UserSpecialty").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}