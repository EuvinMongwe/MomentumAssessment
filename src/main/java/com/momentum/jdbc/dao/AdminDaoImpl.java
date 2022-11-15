/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.momentum.jdbc.dao;

import com.momentum.jdbc.dao.persistence.PersistenceManager;
import com.momentum.jdbc.dao.persistence.entity.InvestorEntity;
import com.momentum.jdbc.dao.persistence.entity.LoginEntity;
import com.momentum.jdbc.dao.persistence.entity.ProductEntity;
import java.util.List;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author euvinmongwe
 */
@Repository
public class AdminDaoImpl implements AdminDao {

    /**
     * Database persistence manager
     */
    private final PersistenceManager persistenceManager;

    /**
     * Default constructor
     *
     * @param persistenceManager persistence manager
     */
    @Autowired
    public AdminDaoImpl(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }
    
       public void resetPassword(LoginEntity loginEntity) throws DataException {
         persistenceManager.update(loginEntity);
    }
    
    public List<LoginEntity> getLogins(String investorid) throws DataException{
        return (List<LoginEntity>) persistenceManager.findWithQuery(" FROM login WHERE investorid ='" + investorid + "'");
    }

    /**
     * Retrieves investors information using investorid
     *
     * @param investorid
     * @return
     */
    public List<InvestorEntity> getInvestorInfo(String investorid) throws DataException{
        return (List<InvestorEntity>) persistenceManager.findWithQuery(" from investorentity where investorid ='" + investorid + "'");
    }
    
    /**
     * Retrieves products information
     *
     * @param productID
     * @return
     */
    public List<ProductEntity> getProductInfo(String productID) throws DataException{
        return (List<ProductEntity>) persistenceManager.findWithQuery(" from productentity WHERE investorid ='" + productID + "'");
    }

    /**
     * Withdraw Money
     *
     * @param product
     */
    public void withdraw(ProductEntity product) throws DataException{
        persistenceManager.update(product);

    }

}
