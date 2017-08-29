/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
@Configuration
public class CommonBeans {

    @Bean
    public NamedParameterJdbcTemplate template() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

    @Bean
    public DataSource dataSource() {
        Properties p = properties();
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(p.getProperty("dao.driver"));
        ds.setUsername(p.getProperty("dao.username"));
        ds.setPassword(p.getProperty("dao.password"));
        ds.setUrl(p.getProperty("dao.url"));
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public Properties properties() {
        Properties properties = new Properties();
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("dao.properties");
        try {
            properties.load(stream);
        } catch (IOException ex) {
            Logger.getLogger(CommonBeans.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }
}
