package com.epam.esm.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Class contains spring test persistence configuration.
 *
 * @author Maryia Kuksar
 * @version 1.0
 */
public class TestPersistenceConfiguration extends PersistenceConfiguration{
    @Value("classpath:create_tables_script.sql")
    private String createTablesScript;
    @Value("classpath:init_tables_script.sql")
    private String initTablesScript;

    /**
     * Creates bean DataSource for working with test database
     *
     * @return the data source
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(createTablesScript)
                .addScript(initTablesScript)
                .build();
    }
}
