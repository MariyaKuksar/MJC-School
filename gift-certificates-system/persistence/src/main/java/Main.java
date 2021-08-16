import com.epam.esm.configuration.PersistenceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PersistenceConfiguration.class);
        GiftCertificateDao giftCertificateDao = (GiftCertificateDao) applicationContext.getBean(GiftCertificateDao.class);
        TagDao tagDao = (TagDao) applicationContext.getBean(TagDao.class);

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Happy Birthday");
        giftCertificate.setDescription("gift certificate");
        giftCertificate.setPrice(new BigDecimal(50));
        giftCertificate.setDuration(365);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());

        Tag tag = new Tag();
        tag.setName("happyBirthday");

        System.out.println(giftCertificateDao.create(giftCertificate));
        System.out.println(giftCertificateDao.findAll());
        System.out.println(giftCertificateDao.findById(1));
        giftCertificateDao.delete(4);

        System.out.println(tagDao.create(tag));
        System.out.println(tagDao.findAll());
        System.out.println(tagDao.findById(2));
        tagDao.delete(4);
    }
}
