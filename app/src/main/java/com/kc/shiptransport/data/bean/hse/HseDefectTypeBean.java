package com.kc.shiptransport.data.bean.hse;

/**
 * @author 邱永恒
 * @time 2017/11/24  14:16
 * @desc ${TODD}
 */

public class HseDefectTypeBean {
    private String Name;
    private int SortNum;

    public HseDefectTypeBean(String name, int sortNum) {
        Name = name;
        SortNum = sortNum;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSortNum() {
        return SortNum;
    }

    public void setSortNum(int sortNum) {
        SortNum = sortNum;
    }
}
