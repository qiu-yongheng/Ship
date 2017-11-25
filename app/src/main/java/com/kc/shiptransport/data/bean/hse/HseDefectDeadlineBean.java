package com.kc.shiptransport.data.bean.hse;

/**
 * @author 邱永恒
 * @time 2017/11/24  14:24
 * @desc ${TODD}
 */

public class HseDefectDeadlineBean {
    private String RectificationDeadlineName;

    public HseDefectDeadlineBean(String rectificationDeadlineName) {
        RectificationDeadlineName = rectificationDeadlineName;
    }

    public String getRectificationDeadlineName() {
        return RectificationDeadlineName;
    }

    public void setRectificationDeadlineName(String rectificationDeadlineName) {
        RectificationDeadlineName = rectificationDeadlineName;
    }
}
