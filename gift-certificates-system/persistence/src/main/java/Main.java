import com.epam.esm.configuration.PersistenceConfiguration;
import com.epam.esm.configuration.TestPersistenceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateSearchParams;
import com.epam.esm.entity.SortingOrder;
import com.epam.esm.entity.Tag;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
//todo
public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PersistenceConfiguration.class);
        GiftCertificateDao giftCertificateDao = applicationContext.getBean(GiftCertificateDao.class);
        TagDao tagDao = applicationContext.getBean(TagDao.class);

//        GiftCertificate giftCertificate = new GiftCertificate();
//        giftCertificate.setName("Happy Birthday");
//        giftCertificate.setDescription("gift certificate");
//        giftCertificate.setPrice(new BigDecimal(50));
//        giftCertificate.setDuration(365);
//        giftCertificate.setCreateDate(ZonedDateTime.now(ZoneOffset.UTC));
//        giftCertificate.setLastUpdateDate(ZonedDateTime.now(ZoneOffset.UTC));
//
//        Tag tag = new Tag();
//        tag.setName("happyBirthday");
//
//        System.out.println(giftCertificateDao.create(giftCertificate));
//        System.out.println(giftCertificateDao.findAll());
//        System.out.println(giftCertificateDao.findById(1));
//        giftCertificate.setId(2);
//        System.out.println(giftCertificateDao.update(giftCertificate));
//        giftCertificateDao.delete(5);
//
//        System.out.println(tagDao.create(tag));
//        System.out.println(tagDao.findAll());
//        System.out.println(tagDao.findById(2));
//        tagDao.delete(3);
        GiftCertificateSearchParams searchParams = new GiftCertificateSearchParams();
        searchParams.setTagName("woman");
        System.out.println(giftCertificateDao.findBySearchParams(searchParams));
    }
}
