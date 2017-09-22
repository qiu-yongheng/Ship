package com.kc.shiptransport.db.sample;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/9/21  17:17
 * @desc 验砂取样数据
 */

public class SampleData extends DataSupport{

    /**
     * ItemID : 3082
     * Creator : csfbs
     * SandSamplingDate : 2017-09-21 11:29:00
     * SubcontractorInterimApproachPlanID : 7608
     * ConstructionBoatAccount :
     * ShipName :
     * Batch : ggggg
     * NQAA :
     * IsExit : 0
     * SandSamplingNumRecordList : [{"ItemID":4093,"SandSamplingID":3082,"SamplingNum":"gggggA","SandSamplingAttachmentRecordList":[{"ItemID":4109,"SandSamplingID":3082,"SandSamplingNumID":4093,"FileName":"I01032925.jpeg","FilePath":"https://cchk3.kingwi.org/Files/20170921/06c191c7-164d-4729-a448-a47b20965418.jpeg"},{"ItemID":4110,"SandSamplingID":3082,"SandSamplingNumID":4093,"FileName":"E1F5BB0628D5EA21A841AFE506BF19B2.png","FilePath":"https://cchk3.kingwi.org/Files/20170921/dcf6e6f7-7cc4-4333-a8fa-ef295a5eee28.png"},{"ItemID":4111,"SandSamplingID":3082,"SandSamplingNumID":4093,"FileName":"5E456B41278782BD50F6E8327F9C7F98.png","FilePath":"https://cchk3.kingwi.org/Files/20170921/66ca89aa-5e6e-400d-9d19-59396192d0a5.png"}],"ConstructionBoatAccount":"cssgcb","ConstructionBoatAccountName":"测试施工船舶"},{"ItemID":4094,"SandSamplingID":3082,"SamplingNum":"gggggB","SandSamplingAttachmentRecordList":[{"ItemID":4112,"SandSamplingID":3082,"SandSamplingNumID":4094,"FileName":"5E456B41278782BD50F6E8327F9C7F98.png","FilePath":"https://cchk3.kingwi.org/Files/20170921/b5b7b59a-5102-425c-8933-c8f23fb42e61.png"}],"ConstructionBoatAccount":"jx6","ConstructionBoatAccountName":"吉星6"},{"ItemID":4095,"SandSamplingID":3082,"SamplingNum":"gggggC","SandSamplingAttachmentRecordList":[],"ConstructionBoatAccount":"","ConstructionBoatAccountName":""}]
     */

    private int ItemID;
    private String Creator;
    private String SandSamplingDate;
    private int SubcontractorInterimApproachPlanID;
    private String ConstructionBoatAccount;
    private String ShipName;
    private String Batch;
    private String NQAA;
    private String IsExit;
    private List<SandSamplingNumRecordListBean> SandSamplingNumRecordList;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public String getSandSamplingDate() {
        return SandSamplingDate;
    }

    public void setSandSamplingDate(String SandSamplingDate) {
        this.SandSamplingDate = SandSamplingDate;
    }

    public int getSubcontractorInterimApproachPlanID() {
        return SubcontractorInterimApproachPlanID;
    }

    public void setSubcontractorInterimApproachPlanID(int SubcontractorInterimApproachPlanID) {
        this.SubcontractorInterimApproachPlanID = SubcontractorInterimApproachPlanID;
    }

    public String getConstructionBoatAccount() {
        return ConstructionBoatAccount;
    }

    public void setConstructionBoatAccount(String ConstructionBoatAccount) {
        this.ConstructionBoatAccount = ConstructionBoatAccount;
    }

    public String getShipName() {
        return ShipName;
    }

    public void setShipName(String ShipName) {
        this.ShipName = ShipName;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String Batch) {
        this.Batch = Batch;
    }

    public String getNQAA() {
        return NQAA;
    }

    public void setNQAA(String NQAA) {
        this.NQAA = NQAA;
    }

    public String getIsExit() {
        return IsExit;
    }

    public void setIsExit(String IsExit) {
        this.IsExit = IsExit;
    }

    public List<SandSamplingNumRecordListBean> getSandSamplingNumRecordList() {
        return SandSamplingNumRecordList;
    }

    public void setSandSamplingNumRecordList(List<SandSamplingNumRecordListBean> SandSamplingNumRecordList) {
        this.SandSamplingNumRecordList = SandSamplingNumRecordList;
    }
}
