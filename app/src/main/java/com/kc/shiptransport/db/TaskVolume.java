package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/6/10 9:41
 * @desc ${TODO}
 */

public class TaskVolume extends DataSupport{

    /**
     * ItemID : 1.0
     * SubcontractorAccount : yflf
     * SubcontractorName : 誉丰联发
     * Date : 2017-06-07T00:00:00
     * BoatA : 1000.0
     * BoatB : 5000.0
     * BoatC : 2000.0
     * BoatD : 0.0
     * AllBoatSum : 8000.0
     */

    private double ItemID;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String Date;
    private double BoatA;
    private double BoatB;
    private double BoatC;
    private double BoatD;
    private double AllBoatSum;

    public double getItemID() {
        return ItemID;
    }

    public void setItemID(double ItemID) {
        this.ItemID = ItemID;
    }

    public String getSubcontractorAccount() {
        return SubcontractorAccount;
    }

    public void setSubcontractorAccount(String SubcontractorAccount) {
        this.SubcontractorAccount = SubcontractorAccount;
    }

    public String getSubcontractorName() {
        return SubcontractorName;
    }

    public void setSubcontractorName(String SubcontractorName) {
        this.SubcontractorName = SubcontractorName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public double getBoatA() {
        return BoatA;
    }

    public void setBoatA(double BoatA) {
        this.BoatA = BoatA;
    }

    public double getBoatB() {
        return BoatB;
    }

    public void setBoatB(double BoatB) {
        this.BoatB = BoatB;
    }

    public double getBoatC() {
        return BoatC;
    }

    public void setBoatC(double BoatC) {
        this.BoatC = BoatC;
    }

    public double getBoatD() {
        return BoatD;
    }

    public void setBoatD(double BoatD) {
        this.BoatD = BoatD;
    }

    public double getAllBoatSum() {
        return AllBoatSum;
    }

    public void setAllBoatSum(double AllBoatSum) {
        this.AllBoatSum = AllBoatSum;
    }
}
