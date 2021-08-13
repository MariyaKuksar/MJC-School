import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mySQL://127.0.0.1:3306/gift_certificates_system");
        dataSource.setUsername("root");
        dataSource.setPassword("2556795leha");

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Happy Birthday");
        giftCertificate.setDescription("gift certificate");
        giftCertificate.setPrice(new BigDecimal(50));
        giftCertificate.setDuration(365);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());

        GiftCertificateDaoImpl dao = new GiftCertificateDaoImpl(new JdbcTemplate(dataSource));

        dao.create(giftCertificate);
        System.out.println(dao.findAll());
        System.out.println(dao.findById(3));
    }
}
