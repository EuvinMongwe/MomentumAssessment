/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.momentum.jdbc.dao.persistence.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author euvinmongwe
 */
@Entity(name = "productentity")
@Table(name = "productentity")
public class ProductEntity implements Serializable{

    @Column(name = "investorid")
    @Id
    private String investorid;

    @Column(name = "savingID")
    @Id
    private String savingID;

    @Column(name = "lastSaving")
    private Timestamp lastSaving;

    @Column(name = "saveDate")
    private Timestamp saveDate;

    @Column(name = "savingPreviousBalance")
    private String savingPreviousBalance;

    @Column(name = "savingBalance")
    private String savingBalance;

    @Column(name = "retirementID")
    @Id
    private String retirementID;

    @Column(name = "retirementPreviousBalance")
    private String retirementPreviousBalance;

    @Column(name = "retirementBalance")
    private String retirementBalance;

    @Column(name = "lastRetire")
    private Timestamp lastRetire;

    @Column(name = "retireeDate")
    private Timestamp retireDate;

    public String getInvestorid() {
        return investorid;
    }

    public void setInvestorid(String investorid) {
        this.investorid = investorid;
    }

    public String getSavingPreviousBalance() {
        return savingPreviousBalance;
    }

    public void setSavingPreviousBalance(String savingPreviousBalance) {
        this.savingPreviousBalance = savingPreviousBalance;
    }

    public String getSavingBalance() {
        return savingBalance;
    }

    public void setSavingBalance(String savingBalance) {
        this.savingBalance = savingBalance;
    }

    public String getRetirementPreviousBalance() {
        return retirementPreviousBalance;
    }

    public void setRetirementPreviousBalance(String retirementPreviousBalance) {
        this.retirementPreviousBalance = retirementPreviousBalance;
    }

    public String getRetirementBalance() {
        return retirementBalance;
    }

    public void setRetirementBalance(String retirementBalance) {
        this.retirementBalance = retirementBalance;
    }

    public Timestamp getLastSaving() {
        return lastSaving;
    }

    public void setLastSaving(Timestamp lastSaving) {
        this.lastSaving = lastSaving;
    }

    public Timestamp getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Timestamp saveDate) {
        this.saveDate = saveDate;
    }

    public Timestamp getLastRetire() {
        return lastRetire;
    }

    public void setLastRetire(Timestamp lastRetire) {
        this.lastRetire = lastRetire;
    }

    public Timestamp getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(Timestamp retireDate) {
        this.retireDate = retireDate;
    }

    public String getSavingID() {
        return savingID;
    }

    public void setSavingID(String savingID) {
        this.savingID = savingID;
    }

    public String getRetirementID() {
        return retirementID;
    }

    public void setRetirementID(String retirementID) {
        this.retirementID = retirementID;
    }

    @Override
    public String toString() {
        return "ProductEntity{" + "investorid=" + investorid + ", savingID=" + savingID + ", lastSaving=" + lastSaving + ", saveDate=" + saveDate + ", savingPreviousBalance=" + savingPreviousBalance + ", savingBalance=" + savingBalance + ", retirementID=" + retirementID + ", retirementPreviousBalance=" + retirementPreviousBalance + ", retirementBalance=" + retirementBalance + ", lastRetire=" + lastRetire + ", retireDate=" + retireDate + '}';
    }

}
