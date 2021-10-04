package com.epam.esm.entity.audit;

import com.epam.esm.entity.GiftCertificate;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.ZonedDateTime;

/**
 * Class is listener for gift certificate entity.
 *
 * @author Maryia_Kuksar
 * @version 1.0
 */
public class GiftCertificateAudit {

    @PrePersist
    public void beforeCreateGiftCertificate(GiftCertificate giftCertificate) {
        ZonedDateTime currentDate = ZonedDateTime.now();
        giftCertificate.setCreateDate(currentDate);
        giftCertificate.setLastUpdateDate(currentDate);
    }

    @PreUpdate
    public void beforeUpdateGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificate.setLastUpdateDate(ZonedDateTime.now());
    }
}