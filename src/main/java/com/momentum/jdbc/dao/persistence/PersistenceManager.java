/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.momentum.jdbc.dao.persistence;

import com.momentum.config.AppConfig;
import com.momentum.jdbc.dao.persistence.entity.InvestorEntity;
import com.momentum.jdbc.dao.persistence.entity.LoginEntity;
import com.momentum.jdbc.dao.persistence.entity.ProductEntity;
import java.util.List;
import java.util.Properties;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Repository;


/**
 *
 * @author euvinmongwe
 */
@Repository
public class PersistenceManager {
    
    
    private static final Logger log = Logger.getLogger(PersistenceManager.class);
    
    /**
     * Current database opened session
     */
    private Session databaseSession;

    /**
     * Current database transaction
     */
    private Transaction transaction;

    /**
     * Session factory
     */
    private static SessionFactory sessionFactory;

    /**
     * DB_PROPERTIES
     */
    private static final String DB_PROPERTIES = "/database/jdbc.properties";

    /**
     * Gets the SessionFactory and opens a new database session
     */
    private void openSession() {
        databaseSession = getSessionFactory().openSession();
    }

    /**
     * Opens a new database sessions and creates a transactions
     */
    private void beginTransaction() {
        setDatabaseSession(getSessionFactory().openSession());
        transaction = databaseSession.beginTransaction();
    }

    /**
     * Closes the current database session
     */
    private void closeSession() {
        databaseSession.close();
    }

    /**
     * Commits the current transaction and closes the session
     */
    private void closeTransactionAndCommit() {
        transaction.commit();
        databaseSession.close();
    }

    /**
     * Creates our database SessionFactory from the Hibernate config.
     *
     * @return database session factory
     */
    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            Properties dbProps = AppConfig.getProperties(DB_PROPERTIES);

            settings.put(Environment.DRIVER, dbProps.getProperty("postgresql.datasource.driverClassName"));
            settings.put(Environment.URL, dbProps.getProperty("postgresql.datasource.url"));
            settings.put(Environment.USER, dbProps.getProperty("postgresql.datasource.username"));
            settings.put(Environment.PASS, dbProps.getProperty("postgresql.datasource.password"));
            settings.put(Environment.DIALECT, dbProps.getProperty("postgresql.jpa.hibernate.dialect"));
            settings.put(Environment.SHOW_SQL, dbProps.getProperty("postgresql.jpa.show-sql"));
//            settings.put(Environment.DEFAULT_SCHEMA, dbProps.getProperty("postgresql.datasource.schema"));
            settings.put(Environment.POOL_SIZE, 5);
            //settings.put(Environment.HBM2DDL_AUTO, dbProps.getProperty("postgresql.jpa.hibernate.ddl-auto"));

            configuration.setProperties(settings);
            configuration.addAnnotatedClass(InvestorEntity.class);
            configuration.addAnnotatedClass(LoginEntity.class);
            configuration.addAnnotatedClass(ProductEntity.class);

            ServiceRegistry builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(builder);
        }
        return sessionFactory;
    }

    /**
     * Persists the given object to the database
     *
     * @param entity object to save
     */
    public void persist(Object entity) {
        beginTransaction();
        getDatabaseSession().save(entity);
        closeTransactionAndCommit();
    }

    /**
     * Updates the given object in the database.
     *
     * @param entity object to update
     */
    public void update(Object entity) {
        beginTransaction();
        getDatabaseSession().update(entity);
        closeTransactionAndCommit();
    }

    /**
     * 
     * @param query
     * @return 
     */
    public List findWithQuery(String query) {
        openSession();
        Query databaseQuery = getDatabaseSession().createQuery(query);
        List list = databaseQuery.list();
        closeSession();
        return list;
    }

    /**
     * Gets the database session
     *
     * @return databaseSession
     */
    private Session getDatabaseSession() {
        return databaseSession;
    }

    /**
     * Sets the created database session for use
     *
     * @param databaseSession database session
     */
    public void setDatabaseSession(Session databaseSession) {
        this.databaseSession = databaseSession;
    }

    /**
     * Gets the database transaction linked to this session
     *
     * @return database transaction in action
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Sets the database transaction for use
     *
     * @param transaction database transaction
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}

    
