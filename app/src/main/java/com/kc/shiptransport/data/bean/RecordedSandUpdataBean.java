package com.kc.shiptransport.data.bean;

/**
 * @author 邱永恒
 * @time 2017/6/27  下午11:53
 * @desc 过砂记录提交数据封装类
 */

public class RecordedSandUpdataBean {
    private int ItemID;
    private int SubcontractorInterimApproachPlanID;
    private String SandHandlingShipID;
    private String ConstructionShipID;
    private String StartTime;
    private String EndTime;
    private float BeforeOverSandDraft1;
    private float BeforeOverSandDraft2;
    private float BeforeOverSandDraft3;
    private float BeforeOverSandDraft4;
    private float AfterOverSandDraft1;
    private float AfterOverSandDraft2;
    private float AfterOverSandDraft3;
    private float AfterOverSandDraft4;
    private float ActualAmountOfSand;
    private int IsFinish;
    private String Creator;

    // 供砂船名
    private String SandHandlingShipName;
    // 施工船名
    private String ConstructionShipName;


    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getSubcontractorInterimApproachPlanID() {
        return SubcontractorInterimApproachPlanID;
    }

    public void setSubcontractorInterimApproachPlanID(int subcontractorInterimApproachPlanID) {
        SubcontractorInterimApproachPlanID = subcontractorInterimApproachPlanID;
    }

    public String getSandHandlingShipID() {
        return SandHandlingShipID;
    }

    public void setSandHandlingShipID(String sandHandlingShipID) {
        SandHandlingShipID = sandHandlingShipID;
    }

    public String getConstructionShipID() {
        return ConstructionShipID;
    }

    public void setConstructionShipID(String constructionShipID) {
        ConstructionShipID = constructionShipID;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public float getBeforeOverSandDraft1() {
        return BeforeOverSandDraft1;
    }

    public void setBeforeOverSandDraft1(float beforeOverSandDraft1) {
        BeforeOverSandDraft1 = beforeOverSandDraft1;
    }

    public float getBeforeOverSandDraft2() {
        return BeforeOverSandDraft2;
    }

    public void setBeforeOverSandDraft2(float beforeOverSandDraft2) {
        BeforeOverSandDraft2 = beforeOverSandDraft2;
    }

    public float getBeforeOverSandDraft3() {
        return BeforeOverSandDraft3;
    }

    public void setBeforeOverSandDraft3(float beforeOverSandDraft3) {
        BeforeOverSandDraft3 = beforeOverSandDraft3;
    }

    public float getBeforeOverSandDraft4() {
        return BeforeOverSandDraft4;
    }

    public void setBeforeOverSandDraft4(float beforeOverSandDraft4) {
        BeforeOverSandDraft4 = beforeOverSandDraft4;
    }

    public float getAfterOverSandDraft1() {
        return AfterOverSandDraft1;
    }

    public void setAfterOverSandDraft1(float afterOverSandDraft1) {
        AfterOverSandDraft1 = afterOverSandDraft1;
    }

    public float getAfterOverSandDraft2() {
        return AfterOverSandDraft2;
    }

    public void setAfterOverSandDraft2(float afterOverSandDraft2) {
        AfterOverSandDraft2 = afterOverSandDraft2;
    }

    public float getAfterOverSandDraft3() {
        return AfterOverSandDraft3;
    }

    public void setAfterOverSandDraft3(float afterOverSandDraft3) {
        AfterOverSandDraft3 = afterOverSandDraft3;
    }

    public float getAfterOverSandDraft4() {
        return AfterOverSandDraft4;
    }

    public void setAfterOverSandDraft4(float afterOverSandDraft4) {
        AfterOverSandDraft4 = afterOverSandDraft4;
    }

    public float getActualAmountOfSand() {
        return ActualAmountOfSand;
    }

    public void setActualAmountOfSand(float actualAmountOfSand) {
        ActualAmountOfSand = actualAmountOfSand;
    }

    public int getIsFinish() {
        return IsFinish;
    }

    public void setIsFinish(int isFinish) {
        IsFinish = isFinish;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getSandHandlingShipName() {
        return SandHandlingShipName;
    }

    public void setSandHandlingShipName(String sandHandlingShipName) {
        SandHandlingShipName = sandHandlingShipName;
    }

    public String getConstructionShipName() {
        return ConstructionShipName;
    }

    public void setConstructionShipName(String constructionShipName) {
        ConstructionShipName = constructionShipName;
    }
}
