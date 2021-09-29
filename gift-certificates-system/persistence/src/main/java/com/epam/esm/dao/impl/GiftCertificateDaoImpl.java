package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParams;
import com.epam.esm.entity.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

/**
 * Class is implementation of interface {@link GiftCertificateDao}
 * for working with tag table in database.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String SELECT_GIFT_CERTIFICATE_BY_NAME = "FROM GiftCertificate WHERE name=:name";
    @PersistenceContext
    private EntityManager entityManager;
    private final GiftCertificateQueryCreator queryBuilder;

    @Autowired
    public GiftCertificateDaoImpl(GiftCertificateQueryCreator queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return entityManager.createQuery(SELECT_GIFT_CERTIFICATE_BY_NAME, GiftCertificate.class)
                .setParameter(QueryParam.NAME, name)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<GiftCertificate> findBySearchParams(Pagination pagination, GiftCertificateSearchParams searchParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = queryBuilder.buildQuery(searchParams, criteriaBuilder);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .getResultList();
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        giftCertificate = entityManager.merge(giftCertificate);
        entityManager.flush();
        return giftCertificate;
    }

    @Override
    public boolean delete(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        if (giftCertificate != null) {
            entityManager.remove(giftCertificate);
        }
        return giftCertificate != null;
    }

    @Override
    public long getTotalNumber(GiftCertificateSearchParams searchParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = queryBuilder.buildQuery(searchParams, criteriaBuilder);
        return entityManager.createQuery(criteriaQuery).getResultStream().count();
    }
}
