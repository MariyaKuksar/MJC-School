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
 * for working with gift_certificate table in database.
 *
 * @author Maryia Kuksar
 * @version 1.0
 * @see GiftCertificateDao
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String SELECT_GIFT_CERTIFICATE_BY_ID = "FROM GiftCertificate WHERE deleted=false AND id=:id";
    private static final String SELECT_GIFT_CERTIFICATE_BY_NAME = "FROM GiftCertificate WHERE deleted=false AND name=:name";
    private static final String DELETE_GIFT_CERTIFICATE = "UPDATE GiftCertificate SET deleted=true WHERE id = :id AND deleted = false";
    private static final String DELETE_GIFT_CERTIFICATE_TAG_CONNECTION = "DELETE FROM gift_certificate_tag_connection WHERE gift_certificate_id = :giftCertificateId";
    @PersistenceContext
    private EntityManager entityManager;
    private final GiftCertificateQueryCreator queryCreator;

    @Autowired
    public GiftCertificateDaoImpl(GiftCertificateQueryCreator queryBuilder) {
        this.queryCreator = queryBuilder;
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return entityManager.createQuery(SELECT_GIFT_CERTIFICATE_BY_ID, GiftCertificate.class)
                .setParameter(QueryParam.ID, id)
                .getResultStream()
                .findFirst();
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
        CriteriaQuery<GiftCertificate> criteriaQuery = queryCreator.createCriteriaQuery(searchParams, criteriaBuilder);
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
        return entityManager.createQuery(DELETE_GIFT_CERTIFICATE)
                .setParameter(QueryParam.ID, id)
                .executeUpdate() == 1;
    }

    @Override
    public void deleteConnectionByGiftCertificateId(long id) {
        entityManager.createNativeQuery(DELETE_GIFT_CERTIFICATE_TAG_CONNECTION)
                .setParameter(QueryParam.GIFT_CERTIFICATE_ID, id)
                .executeUpdate();
    }

    @Override
    public long getTotalNumber(GiftCertificateSearchParams searchParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = queryCreator.createCriteriaQuery(searchParams, criteriaBuilder);
        return entityManager.createQuery(criteriaQuery)
                .getResultStream()
                .count();
    }
}
