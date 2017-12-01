package com.kc.shiptransport.data.bean.boatinquire;

/**
 * @author 邱永恒
 * @time 2017/11/30  10:44
 * @desc ${TODD}
 */

public class ItemBean {
    private int SelfCheckItemID;
    private String SelfCheckItemName;
    private int CheckedResult;

    public ItemBean(int selfCheckItemID, String selfCheckItemName, int checkedResult) {
        SelfCheckItemID = selfCheckItemID;
        SelfCheckItemName = selfCheckItemName;
        CheckedResult = checkedResult;
    }

    public int getSelfCheckItemID() {
        return SelfCheckItemID;
    }

    public void setSelfCheckItemID(int selfCheckItemID) {
        SelfCheckItemID = selfCheckItemID;
    }

    public String getSelfCheckItemName() {
        return SelfCheckItemName;
    }

    public void setSelfCheckItemName(String selfCheckItemName) {
        SelfCheckItemName = selfCheckItemName;
    }

    public int getCheckedResult() {
        return CheckedResult;
    }

    public void setCheckedResult(int checkedResult) {
        CheckedResult = checkedResult;
    }
}
