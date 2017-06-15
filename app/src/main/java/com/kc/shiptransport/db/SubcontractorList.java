package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/5/16  15:47
 * @desc ${TODD}
 */

public class SubcontractorList extends DataSupport{

    /**
     * SubcontractorAccount : yflf
     * SubcontractorName : 誉丰联发
     */
    private int id;
    private String SubcontractorAccount;
    private String SubcontractorName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubcontractorAccount() {
        return SubcontractorAccount;
    }

    public void setSubcontractorAccount(String subcontractorAccount) {
        SubcontractorAccount = subcontractorAccount;
    }

    public String getSubcontractorName() {
        return SubcontractorName;
    }

    public void setSubcontractorName(String subcontractorName) {
        SubcontractorName = subcontractorName;
    }
}
