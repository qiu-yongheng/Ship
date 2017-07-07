package com.kc.shiptransport.db.partition;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/7/7  16:25
 * @desc 施工分区编号
 */

public class PartitionNum extends DataSupport{
    private String userAccount;
    private String num;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
