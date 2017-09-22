package com.kc.shiptransport.db.sample;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/9/21  17:21
 * @desc 取样编号
 */

public class SandSamplingNumRecordListBean extends DataSupport{
    /**
     * ItemID : 4093
     * SandSamplingID : 3082
     * SamplingNum : gggggA
     * SandSamplingAttachmentRecordList : [{"ItemID":4109,"SandSamplingID":3082,"SandSamplingNumID":4093,"FileName":"I01032925.jpeg","FilePath":"https://cchk3.kingwi.org/Files/20170921/06c191c7-164d-4729-a448-a47b20965418.jpeg"},{"ItemID":4110,"SandSamplingID":3082,"SandSamplingNumID":4093,"FileName":"E1F5BB0628D5EA21A841AFE506BF19B2.png","FilePath":"https://cchk3.kingwi.org/Files/20170921/dcf6e6f7-7cc4-4333-a8fa-ef295a5eee28.png"},{"ItemID":4111,"SandSamplingID":3082,"SandSamplingNumID":4093,"FileName":"5E456B41278782BD50F6E8327F9C7F98.png","FilePath":"https://cchk3.kingwi.org/Files/20170921/66ca89aa-5e6e-400d-9d19-59396192d0a5.png"}]
     * ConstructionBoatAccount : cssgcb
     * ConstructionBoatAccountName : 测试施工船舶
     */

    private int ItemID;
    private int SandSamplingID;
    private String SamplingNum;
    private String ConstructionBoatAccount;
    private String ConstructionBoatAccountName;
    private List<SandSamplingAttachmentRecordListBean> SandSamplingAttachmentRecordList;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public int getSandSamplingID() {
        return SandSamplingID;
    }

    public void setSandSamplingID(int SandSamplingID) {
        this.SandSamplingID = SandSamplingID;
    }

    public String getSamplingNum() {
        return SamplingNum;
    }

    public void setSamplingNum(String SamplingNum) {
        this.SamplingNum = SamplingNum;
    }

    public String getConstructionBoatAccount() {
        return ConstructionBoatAccount;
    }

    public void setConstructionBoatAccount(String ConstructionBoatAccount) {
        this.ConstructionBoatAccount = ConstructionBoatAccount;
    }

    public String getConstructionBoatAccountName() {
        return ConstructionBoatAccountName;
    }

    public void setConstructionBoatAccountName(String ConstructionBoatAccountName) {
        this.ConstructionBoatAccountName = ConstructionBoatAccountName;
    }

    public List<SandSamplingAttachmentRecordListBean> getSandSamplingAttachmentRecordList() {
        return SandSamplingAttachmentRecordList;
    }

    public void setSandSamplingAttachmentRecordList(List<SandSamplingAttachmentRecordListBean> SandSamplingAttachmentRecordList) {
        this.SandSamplingAttachmentRecordList = SandSamplingAttachmentRecordList;
    }

    public static class SandSamplingAttachmentRecordListBean {
        /**
         * ItemID : 4109
         * SandSamplingID : 3082
         * SandSamplingNumID : 4093
         * FileName : I01032925.jpeg
         * FilePath : https://cchk3.kingwi.org/Files/20170921/06c191c7-164d-4729-a448-a47b20965418.jpeg
         */

        private int ItemID;
        private int SandSamplingID;
        private int SandSamplingNumID;
        private String FileName;
        private String FilePath;

        public int getItemID() {
            return ItemID;
        }

        public void setItemID(int ItemID) {
            this.ItemID = ItemID;
        }

        public int getSandSamplingID() {
            return SandSamplingID;
        }

        public void setSandSamplingID(int SandSamplingID) {
            this.SandSamplingID = SandSamplingID;
        }

        public int getSandSamplingNumID() {
            return SandSamplingNumID;
        }

        public void setSandSamplingNumID(int SandSamplingNumID) {
            this.SandSamplingNumID = SandSamplingNumID;
        }

        public String getFileName() {
            return FileName;
        }

        public void setFileName(String FileName) {
            this.FileName = FileName;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }
    }
}
