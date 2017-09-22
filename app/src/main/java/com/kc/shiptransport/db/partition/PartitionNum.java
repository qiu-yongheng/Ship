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
    private int tag = 0; // 1 正常 0 长度不对

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

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
