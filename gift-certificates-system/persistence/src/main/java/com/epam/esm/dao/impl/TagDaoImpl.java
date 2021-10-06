package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Class is implementation of interface {@link TagDao}
 * for working with tag table in database.
 *
 * @author Maryia Kuksar
 * @version 1.0
 * @see TagDao
 */
@Repository
public class TagDaoImpl implements TagDao {
    private static final String SELECT_ALL_TAGS = "FROM Tag";
    private static final String SELECT_TAG_BY_NAME = "FROM Tag WHERE name=:name";
    private static final String SELECT_MOST_POPULAR_TAG = "SELECT tag.id, tag.name FROM tag " +
            "JOIN gift_certificate_tag_connection ON tag.id = gift_certificate_tag_connection.tag_id " +
            "JOIN gift_certificate ON gift_certificate_tag_connection.gift_certificate_id = gift_certificate.id " +
            "JOIN ordered_gift_certificate ON gift_certificate.id = ordered_gift_certificate.gift_certificate_id " +
            "JOIN item_order ON ordered_gift_certificate.order_id = item_order.id WHERE item_order.user_id = " +
            "(SELECT user_id FROM item_order GROUP BY user_id ORDER BY sum(cost) DESC LIMIT 1) " +
            "GROUP BY tag.id ORDER BY count(*) DESC LIMIT 1";
    private static final String SELECT_TOTAL_NUMBER_TAGS = "SELECT COUNT(*) FROM Tag";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public List<Tag> findAll(Pagination pagination) {
        return entityManager.createQuery(SELECT_ALL_TAGS, Tag.class)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .getResultList();
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return entityManager.createQuery(SELECT_TAG_BY_NAME, Tag.class)
                .setParameter(QueryParam.NAME, name)
                .getResultStream()
                .findFirst();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<Tag> findMostPopularTagOfUserWithHighestCostOfAllOrders() {
        return entityManager.createNativeQuery(SELECT_MOST_POPULAR_TAG, Tag.class)
                .getResultStream()
                .findFirst();
    }

    @Override
    public boolean delete(long id) {
        Tag tag = entityManager.getReference(Tag.class, id);
        if (tag != null) {
            entityManager.remove(tag);
        }
        return tag != null;
    }

    @Override
    public long getTotalNumber() {
        return (Long) entityManager.createQuery(SELECT_TOTAL_NUMBER_TAGS).getSingleResult();
    }
}
