package com.kc.shiptransport.data.bean;

/**
 * @author qiuyongheng
 * @time 2017/6/26  9:19
 * @desc 保存要提交的验砂取样数据
 */

public class SampleCommitList{
    private byte[] ByteData;
    private String FileName;
    private String SuffixName;
    private int ItemID;
    private int SubcontractorInterimApproachPlanID;
    private String ConstructionBoatAccount;
    private String SamplingNum;
    private String Creator;

    public byte[] getByteData() {
        return ByteData;
    }

    public void setByteData(byte[] byteData) {
        ByteData = byteData;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getSuffixName() {
        return SuffixName;
    }

    public void setSuffixName(String suffixName) {
        SuffixName = suffixName;
    }

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

    public String getConstructionBoatAccount() {
        return ConstructionBoatAccount;
    }

    public void setConstructionBoatAccount(String constructionBoatAccount) {
        ConstructionBoatAccount = constructionBoatAccount;
    }

    public String getSamplingNum() {
        return SamplingNum;
    }

    public void setSamplingNum(String samplingNum) {
        SamplingNum = samplingNum;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }
}
