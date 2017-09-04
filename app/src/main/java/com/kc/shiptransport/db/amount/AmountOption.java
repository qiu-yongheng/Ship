package com.kc.shiptransport.db.amount;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/9/4  13:33
 * @desc ${TODD}
 */

public class AmountOption extends DataSupport{

    /**
     * UserID : liangfangrenyuan1
     * UserName : 量方人员1
     * SortNum : 1
     */

    private String UserID;
    private String UserName;
    private int SortNum;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public int getSortNum() {
        return SortNum;
    }

    public void setSortNum(int SortNum) {
        this.SortNum = SortNum;
    }
}
