import com.epam.esm.configuration.ServiceConfiguration;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//todo
public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ServiceConfiguration.class);
        GiftCertificateService giftCertificateService = applicationContext.getBean(GiftCertificateService.class);
        System.out.println(giftCertificateService.findGiftCertificateById(21));
    }
}
