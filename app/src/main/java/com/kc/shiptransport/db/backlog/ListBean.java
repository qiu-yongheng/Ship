package com.kc.shiptransport.db.backlog;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/9/8  22:05
 * @desc ${TODD}
 */


public class ListBean extends DataSupport {
    /**
     * SubcontractorInterimApproachPlanID : 6586
     * SubcontractorAccount : csfbs
     * SubcontractorName : 测试分包商
     * PlanDay : 2017-09-07
     * ShipAccount : ydgh0768
     * ShipName : 粤东莞货0768
     * SandSupplyCount : 3500
     */

    private int SubcontractorInterimApproachPlanID;
    private String SubcontractorAccount;
    private String SubcontractorName;
    private String PlanDay;
    private String ShipAccount;
    private String ShipName;
    private String SandSupplyCount;
    private int SandSubcontractorPreAcceptanceEvaluationID; // 预验收评价ID

    public int getSandSubcontractorPreAcceptanceEvaluationID() {
        return SandSubcontractorPreAcceptanceEvaluationID;
    }

    public void setSandSubcontractorPreAcceptanceEvaluationID(int sandSubcontractorPreAcceptanceEvaluationID) {
        SandSubcontractorPreAcceptanceEvaluationID = sandSubcontractorPreAcceptanceEvaluationID;
    }

    public int getSubcontractorInterimApproachPlanID() {
        return SubcontractorInterimApproachPlanID;
    }

    public void setSubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) {
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
