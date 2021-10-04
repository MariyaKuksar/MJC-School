package com.epam.esm.configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Class contains spring persistence configuration.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.epam.esm")
@EntityScan(basePackages = "com.epam.esm")
public class PersistenceConfiguration {
}
