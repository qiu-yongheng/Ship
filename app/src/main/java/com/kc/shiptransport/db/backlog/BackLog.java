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

    public static class ListBean {
        /**
         * SubcontractorInterimApproachPlanID : 6586
         * SubcontractorAccount : csfbs
         * SubcontractorName : 测试分包商
         * PlanDay : 2017-09-07
         * ShipAccount : ydgh0768
         * ShipName : 粤东莞货0768
         * SandSupplyCount : 3500
         */

        private String SubcontractorInterimApproachPlanID;
        private String SubcontractorAccount;
        private String SubcontractorName;
        private String PlanDay;
        private String ShipAccount;
        private String ShipName;
        private String SandSupplyCount;

        public String getSubcontractorInterimApproachPlanID() {
            return SubcontractorInterimApproachPlanID;
        }

        public void setSubcontractorInterimApproachPlanID(String SubcontractorInterimApproachPlanID) {
            this.SubcontractorInterimApproachPlanID = SubcontractorInterimApproachPlanID;
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

        public String getPlanDay() {
            return PlanDay;
        }

        public void setPlanDay(String PlanDay) {
            this.PlanDay = PlanDay;
        }

        public String getShipAccount() {
            return ShipAccount;
        }

        public void setShipAccount(String ShipAccount) {
            this.ShipAccount = ShipAccount;
        }

        public String getShipName() {
            return ShipName;
        }

        public void setShipName(String ShipName) {
            this.ShipName = ShipName;
        }

        public String getSandSupplyCount() {
            return SandSupplyCount;
        }

        public void setSandSupplyCount(String SandSupplyCount) {
            this.SandSupplyCount = SandSupplyCount;
        }
    }
}
