/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.momentum.jdbc.dao;

import com.momentum.jdbc.dao.persistence.entity.InvestorEntity;
import com.momentum.jdbc.dao.persistence.entity.LoginEntity;
import com.momentum.jdbc.dao.persistence.entity.ProductEntity;
import java.util.List;
import org.hibernate.exception.DataException;

/**
 *
 * @author euvinmongwe
 */
public interface AdminDao {

    /**
     *
     * @param loginEntity
     * @throws DataException
     */
    public void resetPassword(LoginEntity loginEntity) throws DataException;

    /**
     *
     * @param investorID
     * @return
     */
    public List<LoginEntity> getLogins(String investorID) throws DataException;

    /**
     *
     * @param investorID
     * @return
     */
    public List<InvestorEntity> getInvestorInfo(String investorID) throws DataException;

    /**
     *
     * @param productID
     * @return
     */
    public List<ProductEntity> getProductInfo(String productID) throws DataException;

    /**
     *
     * @param product
     */
    public void withdraw(ProductEntity product) throws DataException;
}
