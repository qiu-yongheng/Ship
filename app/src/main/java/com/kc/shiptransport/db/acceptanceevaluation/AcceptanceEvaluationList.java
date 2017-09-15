package com.kc.shiptransport.db.acceptanceevaluation;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/8/7  9:10
 * @desc 预验收评价
 */

public class AcceptanceEvaluationList extends DataSupport{

    /**
     * rownumber : 1
     * ItemID : 6078
     * MaterialIntegrity : 2.0
     * MaterialTimeliness : 2.0
     * PreAcceptanceTime : 2017-09-06 04:33:00
     * SubcontractorInterimApproachPlanID : 6585
     * PlanDay : 2017-09-07
     * ShipAccount : csgscb
     * ShipName : 测试供砂船舶
     * SubcontractorAccount : csfbs
     * SubcontractorName : 测试分包商
     * StatusRemark : 已阅
     */

    private int rownumber;
    private int ItemID;
    private float MaterialIntegrity;
    private float MaterialTimeliness;
    private String PreAcceptanceTime;
    private int SubcontractorInterimApproachPlanID;
    private String PlanDay;
    private String ShipAccount;
    private String ShipName;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String StatusRemark;
    private String Remark; // 验收备注
    private String RemarkForReceptionSandReject; // 验砂不通过意见

    public String getRemarkForReceptionSandReject() {
        return RemarkForReceptionSandReject;
    }

    public void setRemarkForReceptionSandReject(String remarkForReceptionSandReject) {
        RemarkForReceptionSandReject = remarkForReceptionSandReject;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getRownumber() {
        return rownumber;
    }

    public void setRownumber(int rownumber) {
        this.rownumber = rownumber;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public float getMaterialIntegrity() {
        return MaterialIntegrity;
    }

    public void setMaterialIntegrity(float MaterialIntegrity) {
        this.MaterialIntegrity = MaterialIntegrity;
    }

    public float getMaterialTimeliness() {
        return MaterialTimeliness;
    }

    public void setMaterialTimeliness(float MaterialTimeliness) {
        this.MaterialTimeliness = MaterialTimeliness;
    }

    public String getPreAcceptanceTime() {
        return PreAcceptanceTime;
    }

    public void setPreAcceptanceTime(String PreAcceptanceTime) {
        this.PreAcceptanceTime = PreAcceptanceTime;
    }

    public int getSubcontractorInterimApproachPlanID() {
        return SubcontractorInterimApproachPlanID;
    }

    public void setSubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) {
        this.SubcontractorInterimApproachPlanID = SubcontractorInterimApproachPlanID;
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

    public String getStatusRemark() {
        return StatusRemark;
    }

    public void setStatusRemark(String StatusRemark) {
        this.StatusRemark = StatusRemark;
    }
}
