package com.kc.shiptransport.data.bean;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/19  15:34
 * @desc ${TODD}
 */

public class SubmitBean {

    /**
     * SubmitDate : 2017-05-15
     * SubcontractorAccount : yflf
     * RemoveItemIDS : 3,4
     * list : [{"ItemID":"","PlanDay":"2017-05-23","ShipAccount":"bsj618","SandSupplyCount":"3000"},{"ItemID":"","PlanDay":"2017-05-24","ShipAccount":"bsj618","SandSupplyCount":"3000"},{"ItemID":"","PlanDay":"2017-05-25","ShipAccount":"bsj618","SandSupplyCount":"3000"}]
     */

    private String SubmitDate;
    private String SubcontractorAccount;
    private String RemoveItemIDS;
    private List<ListBean> list;

    public String getSubmitDate() {
        return SubmitDate;
    }

    public void setSubmitDate(String SubmitDate) {
        this.SubmitDate = SubmitDate;
    }

    public String getSubcontractorAccount() {
        return SubcontractorAccount;
    }

    public void setSubcontractorAccount(String SubcontractorAccount) {
        this.SubcontractorAccount = SubcontractorAccount;
    }

    public String getRemoveItemIDS() {
        return RemoveItemIDS;
    }

    public void setRemoveItemIDS(String RemoveItemIDS) {
        this.RemoveItemIDS = RemoveItemIDS;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * ItemID :
         * PlanDay : 2017-05-23
         * ShipAccount : bsj618
         * SandSupplyCount : 3000
         */

        private String ItemID;
        private String PlanDay;
        private String ShipAccount;
        private String SandSupplyCount;

        public String getItemID() {
            return ItemID;
        }

        public void setItemID(String ItemID) {
            this.ItemID = ItemID;
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

        public String getSandSupplyCount() {
            return SandSupplyCount;
        }

        public void setSandSupplyCount(String SandSupplyCount) {
            this.SandSupplyCount = SandSupplyCount;
        }
    }
}
