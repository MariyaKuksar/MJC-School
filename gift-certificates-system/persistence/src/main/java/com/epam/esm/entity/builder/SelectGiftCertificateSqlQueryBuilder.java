package com.epam.esm.entity.builder;

import com.epam.esm.entity.GiftCertificateSearchParams;
import com.epam.esm.entity.SqlQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class SelectGiftCertificateSqlQueryBuilder {
    private static final String SQL_SELECT_GIFT_CERTIFICATES = "SELECT GIFT_CERTIFICATE.ID, GIFT_CERTIFICATE" +
            ".NAME," + " DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE, TAG.ID, TAG.NAME FROM " +
            "GIFT_CERTIFICATE " + "LEFT JOIN " + "GIFT_CERTIFICATE_TAG_CONNECTION ON GIFT_CERTIFICATE" + ".ID" +
            "=GIFT_CERTIFICATE_ID LEFT JOIN TAG ON TAG_ID=TAG.ID ";
    private static final String WHERE = "WHERE ";
    private static final String AND = "AND ";
    private static final String TAG_NAME_CONDITION = "GIFT_CERTIFICATE.ID IN (SELECT GIFT_CERTIFICATE_ID FROM " +
            "GIFT_CERTIFICATE_TAG_CONNECTION JOIN TAG ON GIFT_CERTIFICATE_TAG_CONNECTION.TAG_ID=TAG.ID WHERE NAME=?) ";
    private static final String PART_NAME_OR_DESCRIPTION_CONDITION = "(GIFT_CERTIFICATE.NAME LIKE ? OR " +
            "DESCRIPTION LIKE ?) ";
    private static final String ZERO_OR_MORE_CHARACTERS = "%";
    private static final String ORDER_BY = "ORDER BY ";
    private static final String CREATE_DATE = "CREATE_DATE ";
    private static final String GIFT_CERTIFICATE_NAME = "GIFT_CERTIFICATE.NAME ";
    private static final String CONDITION_SEPARATOR = " , ";

    public SqlQuery buildQuery(GiftCertificateSearchParams searchParams) {
        SqlQuery sqlQuery = new SqlQuery();
        StringBuilder query = new StringBuilder();
        query.append(SQL_SELECT_GIFT_CERTIFICATES);
        if (StringUtils.isNotBlank(searchParams.getTagName()) || StringUtils.isNotBlank(searchParams.getPartNameOrDescription())) {
            createQueryCondition(searchParams, query, sqlQuery);
        }
        if (searchParams.getSortingOrderByDate() != null || searchParams.getSortingOrderByName() != null) {
            createConditionsForSortingQueryResults(searchParams, query);
        }
        sqlQuery.setQuery(query.toString());
        return sqlQuery;
    }

    private void createQueryCondition(GiftCertificateSearchParams searchParams, StringBuilder query,
                                      SqlQuery sqlQuery) {
        query.append(WHERE);
        if (StringUtils.isNotBlank(searchParams.getTagName())) {
            query.append(TAG_NAME_CONDITION);
            sqlQuery.addArg(searchParams.getTagName());
            if (StringUtils.isNotBlank(searchParams.getPartNameOrDescription())) {
                query.append(AND);
            }
        }
        if (StringUtils.isNotBlank(searchParams.getPartNameOrDescription())) {
            query.append(PART_NAME_OR_DESCRIPTION_CONDITION);
            sqlQuery.addArg(ZERO_OR_MORE_CHARACTERS + searchParams.getPartNameOrDescription() + ZERO_OR_MORE_CHARACTERS);
            sqlQuery.addArg(ZERO_OR_MORE_CHARACTERS + searchParams.getPartNameOrDescription() + ZERO_OR_MORE_CHARACTERS);
        }
    }

    private void createConditionsForSortingQueryResults(GiftCertificateSearchParams searchParams, StringBuilder query) {
        query.append(ORDER_BY);
        if (searchParams.getSortingOrderByDate() != null) {
            query.append(CREATE_DATE).append(searchParams.getSortingOrderByDate().name());
            if (searchParams.getSortingOrderByName() != null) {
                query.append(CONDITION_SEPARATOR);
            }
        }
        if (searchParams.getSortingOrderByName() != null) {
            query.append(GIFT_CERTIFICATE_NAME).append(searchParams.getSortingOrderByName().name());
        }
    }
}
