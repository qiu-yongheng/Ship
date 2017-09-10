package com.kc.shiptransport.db.backlog;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/9/8  11:54
 * @desc 我的待办
 */

public class BackLog extends DataSupport{

    /**
     * SortNum : 1
     * PendingID : InfomationPerfecting
     * PendingType : 待信息完善
     * List : [{"SubcontractorInterimApproachPlanID":"6586","SubcontractorAccount":"csfbs","SubcontractorName":"测试分包商","PlanDay":"2017-09-07","ShipAccount":"ydgh0768","ShipName":"粤东莞货0768","SandSupplyCount":"3500"},{"SubcontractorInterimApproachPlanID":"6583","SubcontractorAccount":"csfbs","SubcontractorName":"测试分包商","PlanDay":"2017-09-04","ShipAccount":"jh066","ShipName":"建航066","SandSupplyCount":"0"}]
     */

    private int SortNum;
    private String PendingID;
    private String PendingType;
    private java.util.List<ListBean> List;

    public int getSortNum() {
        return SortNum;
    }

    public void setSortNum(int SortNum) {
        this.SortNum = SortNum;
    }

    public String getPendingID() {
        return PendingID;
    }

    public void setPendingID(String PendingID) {
        this.PendingID = PendingID;
    }

    public String getPendingType() {
        return PendingType;
    }

    public void setPendingType(String PendingType) {
        this.PendingType = PendingType;
    }

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }


}
