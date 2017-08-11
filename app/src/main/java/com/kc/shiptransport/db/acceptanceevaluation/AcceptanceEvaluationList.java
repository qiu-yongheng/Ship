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
     * ItemID : 4056
     * PlanDay : 2017-07-30
     * MaterialIntegrity : 5
     * MaterialTimeliness : 5
     * PreAcceptanceTime : 2017-07-28 09:00:00
     * SubcontractorInterimApproachPlanID : 4564
     * ShipAccount : tl8988
     * ShipName : 天力8988
     * SubcontractorAccount : csfbs
     * SubcontractorName : 测试分包商
     */

    private int rownumber;
    private int ItemID;
    private String PlanDay;
    private String MaterialIntegrity;
    private String MaterialTimeliness;
    private String PreAcceptanceTime;
    private int SubcontractorInterimApproachPlanID;
    private String ShipAccount;
    private String ShipName;
    private String SubcontractorAccount;
    private String SubcontractorName;

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

    public String getPlanDay() {
        return PlanDay;
    }

    public void setPlanDay(String PlanDay) {
        this.PlanDay = PlanDay;
    }

    public String getMaterialIntegrity() {
        return MaterialIntegrity;
    }

    public void setMaterialIntegrity(String MaterialIntegrity) {
        this.MaterialIntegrity = MaterialIntegrity;
    }

    public String getMaterialTimeliness() {
        return MaterialTimeliness;
    }

    public void setMaterialTimeliness(String MaterialTimeliness) {
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
}
