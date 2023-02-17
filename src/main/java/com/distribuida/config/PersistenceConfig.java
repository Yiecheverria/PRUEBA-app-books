package com.distribuida.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import java.util.Properties;

@ApplicationScoped
public class PersistenceConfig {
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
    public EntityManager entityManager() {
        return Persistence
                .createEntityManagerFactory("bookspersistence", getPropiedades())
                .createEntityManager();
    }
    private Properties getPropiedades() {
        Properties properties = new Properties();
        properties.put("jakarta.persistence.jdbc.driver", dbDriver);
        properties.put("jakarta.persistence.jdbc.url", dbUrl);
        properties.put("jakarta.persistence.jdbc.user", dbUser);
        properties.put("jakarta.persistence.jdbc.password", dbPassword);
        return properties;
    }
}
