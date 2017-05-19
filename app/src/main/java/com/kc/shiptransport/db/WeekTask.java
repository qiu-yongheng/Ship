package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/5/18  14:19
 * @desc 一周任务分配
 */

public class WeekTask extends DataSupport{
    private int id;
    private String position;
    private String selected;

    // 条目编号
    private int ItemID;
    // 分包商账号
    private String SubcontractorAccount;
    // 计划日期
    private String PlanDay;
    // 船舶账号
    private String ShipAccount;
    // 船舶类型
    private String ShipType;
    // 船舶名
    private String ShipName;
    // 供沙量
    private String SandSupplyCount;


    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String shipName) {
        ShipName = shipName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getSandSupplyCount() {
        return SandSupplyCount;
    }

    public void setSandSupplyCount(String sandSupplyCount) {
        SandSupplyCount = sandSupplyCount;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public String getSubcontractorAccount() {
        return SubcontractorAccount;
    }

    public void setSubcontractorAccount(String subcontractorAccount) {
        SubcontractorAccount = subcontractorAccount;
    }

    public String getPlanDay() {
        return PlanDay;
    }

    public void setPlanDay(String planDay) {
        PlanDay = planDay;
    }

    public String getShipAccount() {
        return ShipAccount;
    }

    public void setShipAccount(String shipAccount) {
        ShipAccount = shipAccount;
    }

    public String getShipType() {
        return ShipType;
    }

    public void setShipType(String shipType) {
        ShipType = shipType;
    }


}
