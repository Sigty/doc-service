package com.itacademy.database.repository;

import com.itacademy.database.dto.LoginDto;
import com.itacademy.database.entity.Role;
import com.itacademy.database.entity.User;
import com.itacademy.database.entity.Role_;
import com.itacademy.database.entity.User_;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

public class ExtendedUserRepositoryImpl implements ExtendedUserRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public LoginDto findByLogin(String login) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<LoginDto> criteria = cb.createQuery(LoginDto.class);
        Root<User> root = criteria.from(User.class);
        Join<User, Role> roleJoin = root.join(User_.role);
        criteria
                .select(cb.construct(LoginDto.class,
                        root.get(User_.login),
                        root.get(User_.password), roleJoin.get(Role_.role)))
                .where(cb.equal(root.get(User_.login), login));
        return entityManager.createQuery(criteria).getSingleResult();
    }
}
