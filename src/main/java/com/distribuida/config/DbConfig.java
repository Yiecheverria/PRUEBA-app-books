package com.distribuida.config;

import com.zaxxer.hikari.HikariDataSource;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.jdbc.ConnectionPool;
import io.helidon.dbclient.jdbc.JdbcDbClientProviderBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;


import javax.sql.DataSource;

@ApplicationScoped
public class DbConfig {
    @Inject
    @ConfigProperty(name="db.connection.url")
    private String dbUrl;

    @Inject
    @ConfigProperty(name="db.connection.username")
    private String dbUser;
    @Inject
    @ConfigProperty(name="db.connection.password")
    private String dbPassword;
    @Inject
    @ConfigProperty(name="db.connection.driver")
    private String dbDriver;

    @Produces
    @ApplicationScoped
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();

        ds.setDriverClassName(dbDriver);
        ds.setJdbcUrl(dbUrl);
        ds.setUsername(dbUser);
        ds.setPassword(dbPassword);

        //ds.setMinimumIdle(1);
        //ds.setMaximumPoolSize(5);

        return ds;
    }
}
