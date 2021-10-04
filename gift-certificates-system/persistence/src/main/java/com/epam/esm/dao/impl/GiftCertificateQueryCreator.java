package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParams;
import com.epam.esm.entity.SortingOrder;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class designed to create query.
 *
 * @author Maryia_Kuksar
 * @version 1.0
 */
@Component
public class GiftCertificateQueryCreator {
    private static final String DELETED = "deleted";
    private static final String TAGS = "tags";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String ZERO_OR_MORE_CHARACTERS = "%";
    private static final String CREATE_DATE = "createDate";

    /**
     * Creates query
     *
     * @param searchParams    data for creating search condition gift certificates
     * @param criteriaBuilder the criteria builder
     * @return the criteria query
     */
    public CriteriaQuery<GiftCertificate> buildQuery(GiftCertificateSearchParams searchParams, CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);
        List<Predicate> restrictions = new ArrayList<>();
        restrictions.add(criteriaBuilder.equal(giftCertificateRoot.get(DELETED), Boolean.FALSE));
        if (searchParams.getTagNames() != null) {
            restrictions.addAll(addTagNames(searchParams.getTagNames(), criteriaBuilder, giftCertificateRoot));
        }
        if (searchParams.getPartNameOrDescription() != null) {
            restrictions.add(addPartNameOrDescription(searchParams.getPartNameOrDescription(), criteriaBuilder, giftCertificateRoot));
        }
        criteriaQuery.select(giftCertificateRoot).where(restrictions.toArray(new Predicate[]{}));
        addSortType(searchParams, criteriaBuilder, criteriaQuery, giftCertificateRoot);
        return criteriaQuery;
    }

    private List<Predicate> addTagNames(List<String> tagNames, CriteriaBuilder criteriaBuilder, Root<GiftCertificate> giftCertificateRoot) {
        return tagNames.stream()
                .map(tagName -> criteriaBuilder.equal(giftCertificateRoot.join(TAGS).get(NAME), tagName))
                .collect(Collectors.toList());

    }

    private Predicate addPartNameOrDescription(String partNameOrDescription, CriteriaBuilder criteriaBuilder, Root<GiftCertificate> giftCertificateRoot) {
        return criteriaBuilder.or
                (criteriaBuilder.like(giftCertificateRoot.get(DESCRIPTION),
                                ZERO_OR_MORE_CHARACTERS + partNameOrDescription + ZERO_OR_MORE_CHARACTERS),
                        criteriaBuilder.like(giftCertificateRoot.get(NAME),
                                ZERO_OR_MORE_CHARACTERS + partNameOrDescription + ZERO_OR_MORE_CHARACTERS));

    }

    private void addSortType(GiftCertificateSearchParams searchParams, CriteriaBuilder criteriaBuilder, CriteriaQuery<GiftCertificate> criteriaQuery, Root<GiftCertificate> giftCertificateRoot) {
        List<Order> orderList = new ArrayList<>();
        SortingOrder sortingOrderByDate = searchParams.getSortingOrderByDate();
        if (sortingOrderByDate != null) {
            Order order = (sortingOrderByDate == SortingOrder.ASC) ?
                    criteriaBuilder.asc(giftCertificateRoot.get(CREATE_DATE)) :
                    criteriaBuilder.desc(giftCertificateRoot.get(CREATE_DATE));
            orderList.add(order);
        }
        SortingOrder sortingOrderByName = searchParams.getSortingOrderByName();
        if (sortingOrderByName != null) {
            Order order = (sortingOrderByName == SortingOrder.ASC) ?
                    criteriaBuilder.asc(giftCertificateRoot.get(NAME)) :
                    criteriaBuilder.desc(giftCertificateRoot.get(NAME));
            orderList.add(order);
        }
        criteriaQuery.orderBy(orderList);
    }
}
