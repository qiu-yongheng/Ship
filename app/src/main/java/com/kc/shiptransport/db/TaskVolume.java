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

    private int ItemID;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String Date;
    private int BoatA;
    private int BoatB;
    private int BoatC;
    private int BoatD;
    private int AllBoatSum;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
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

    public int getBoatA() {
        return BoatA;
    }

    public void setBoatA(int BoatA) {
        this.BoatA = BoatA;
    }

    public int getBoatB() {
        return BoatB;
    }

    public void setBoatB(int BoatB) {
        this.BoatB = BoatB;
    }

    public int getBoatC() {
        return BoatC;
    }

    public void setBoatC(int BoatC) {
        this.BoatC = BoatC;
    }

    public int getBoatD() {
        return BoatD;
    }

    public void setBoatD(int BoatD) {
        this.BoatD = BoatD;
    }

    public int getAllBoatSum() {
        return AllBoatSum;
    }

    public void setAllBoatSum(int AllBoatSum) {
        this.AllBoatSum = AllBoatSum;
    }
}
