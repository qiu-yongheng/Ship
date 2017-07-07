package com.kc.shiptransport.data.bean;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/7  14:03
 * @desc 考勤审核
 */

public class AttendanceAuditCommitBean {

    /**
     * Creator : yflf
     * IsCheck : 1
     * AttendanceCheckList : [{"ItemID":"","AttendanceID":"3","Remark":"通过"},{"ItemID":"","AttendanceID":"1003","Remark":"通过"}]
     */

    private String Creator;
    private String IsCheck;
    private List<AttendanceCheckListBean> AttendanceCheckList;

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public String getIsCheck() {
        return IsCheck;
    }

    public void setIsCheck(String IsCheck) {
        this.IsCheck = IsCheck;
    }

    public List<AttendanceCheckListBean> getAttendanceCheckList() {
        return AttendanceCheckList;
    }

    public void setAttendanceCheckList(List<AttendanceCheckListBean> AttendanceCheckList) {
        this.AttendanceCheckList = AttendanceCheckList;
    }

    public static class AttendanceCheckListBean {
        /**
         * ItemID :
         * AttendanceID : 3
         * Remark : 通过
         */

        private String ItemID;
        private String AttendanceID;
        private String Remark;

        public String getItemID() {
            return ItemID;
        }

        public void setItemID(String ItemID) {
            this.ItemID = ItemID;
        }

        public String getAttendanceID() {
            return AttendanceID;
        }

        public void setAttendanceID(String AttendanceID) {
            this.AttendanceID = AttendanceID;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }
    }
}
