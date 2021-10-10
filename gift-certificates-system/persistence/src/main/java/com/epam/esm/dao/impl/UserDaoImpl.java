package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Class is implementation of interface {@link UserDao}
 * for working with user table in database.
 *
 * @author Maryia_Kuksar
 * @version 1.0
 * @see UserDao
 */
@Repository
public class UserDaoImpl implements UserDao {
    private static final String SELECT_USER_BY_EMAIL = "FROM User WHERE email=:email";
    private static final String SELECT_ALL_USERS = "FROM User";
    private static final String SELECT_TOTAL_NUMBER_USERS = "SELECT COUNT(*) FROM User";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User createUser(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return entityManager.createQuery(SELECT_USER_BY_EMAIL, User.class)
                .setParameter(QueryParam.EMAIL, email)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<User> findAll(Pagination pagination) {
        return entityManager.createQuery(SELECT_ALL_USERS, User.class)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .getResultList();
    }

    @Override
    public long getTotalNumber() {
        return (Long) entityManager.createQuery(SELECT_TOTAL_NUMBER_USERS).getSingleResult();
    }
}
