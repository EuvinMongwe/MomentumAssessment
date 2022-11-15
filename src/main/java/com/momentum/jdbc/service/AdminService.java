/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.momentum.jdbc.service;

import com.momentum.jdbc.dao.AdminDao;
import com.momentum.jdbc.dao.persistence.entity.InvestorEntity;
import com.momentum.jdbc.dao.persistence.entity.LoginEntity;
import com.momentum.jdbc.dao.persistence.entity.ProductEntity;
import java.util.List;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author euvinmongwe
 */
@Service
public class AdminService {

    /**
     * adminDao
     */
    private AdminDao adminDao;

    @Autowired
    public AdminService(AdminDao adminDao) {
        this.adminDao = adminDao;
    }
    
    public void resetPassword(LoginEntity loginEntity) throws DataException {
         adminDao.resetPassword(loginEntity);
    }

    public List<LoginEntity> getLogins(String investorID) throws DataException {
        return adminDao.getLogins(investorID);
    }

    public List<InvestorEntity> getInvestorInfo(String investorID) throws DataException {
        return adminDao.getInvestorInfo(investorID);
    }

    public List<ProductEntity> getProductInfo(String investorID) throws DataException {
        return adminDao.getProductInfo(investorID);
    }

    public void withdraw(ProductEntity product) throws DataException {
        adminDao.withdraw(product);

    }
}
