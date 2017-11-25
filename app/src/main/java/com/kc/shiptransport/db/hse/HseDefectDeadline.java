package com.kc.shiptransport.db.hse;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/11/24  14:23
 * @desc ${TODD}
 */

public class HseDefectDeadline extends DataSupport{

    /**
     * rownumber : 1
     * RectificationDeadlineID : 1
     * RectificationDeadlineName : 1天已内
     * SortNum : 1
     */

    private int rownumber;
    private int RectificationDeadlineID;
    private String RectificationDeadlineName;
    private int SortNum;

    public int getRownumber() {
        return rownumber;
    }

    public void setRownumber(int rownumber) {
        this.rownumber = rownumber;
    }

    public int getRectificationDeadlineID() {
        return RectificationDeadlineID;
    }

    public void setRectificationDeadlineID(int RectificationDeadlineID) {
        this.RectificationDeadlineID = RectificationDeadlineID;
    }

    public String getRectificationDeadlineName() {
        return RectificationDeadlineName;
    }

    public void setRectificationDeadlineName(String RectificationDeadlineName) {
        this.RectificationDeadlineName = RectificationDeadlineName;
    }

    public int getSortNum() {
        return SortNum;
    }

    public void setSortNum(int SortNum) {
        this.SortNum = SortNum;
    }
}
