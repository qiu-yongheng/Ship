package com.kc.shiptransport.data.bean;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/29  11:00
 * @desc 验砂取样信息明细
 */

public class SampleShowDatesBean {

    /**
     * ItemID : 1066
     * Creator : csfbs
     * SandSamplingDate : 2017-07-04 17:58:00
     * SubcontractorInterimApproachPlanID : 3560
     * ConstructionBoatAccount :
     * ShipName :
     * Batch : GG
     * SandSamplingNumRecordList : [{"ItemID":1040,"SandSamplingID":1066,"SamplingNum":"GGA","SandSamplingAttachmentRecordList":[{"ItemID":1064,"SandSamplingID":1066,"SandSamplingNumID":1040,"FileName":"1498011307944.png","FilePath":"https://cchk3.kingwi.org/Files/20170703/310a0cd7-ce18-434c-b871-236ad3c9b419.png"},{"ItemID":1065,"SandSamplingID":1066,"SandSamplingNumID":1040,"FileName":"1497861364341.png","FilePath":"https://cchk3.kingwi.org/Files/20170703/639c6948-1b9c-4eb9-a48c-68e9548c3622.png"}],"ConstructionBoatAccount":"cssgcb","ConstructionBoatAccountName":"测试施工船舶"},{"ItemID":1041,"SandSamplingID":1066,"SamplingNum":"GGB","SandSamplingAttachmentRecordList":[{"ItemID":1068,"SandSamplingID":1066,"SandSamplingNumID":1041,"FileName":"1497861364341.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/0c34dce7-92fc-42ed-b02d-407b7d9d0dde.png"},{"ItemID":1069,"SandSamplingID":1066,"SandSamplingNumID":1041,"FileName":"Screenshot_2017-06-05-14-55-57-935_com.kc.shiptransport.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/1e6a70a0-3745-4075-b5f3-86a909043033.png"},{"ItemID":1070,"SandSamplingID":1066,"SandSamplingNumID":1041,"FileName":"1497861364341.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/0c34dce7-92fc-42ed-b02d-407b7d9d0dde.png"},{"ItemID":1071,"SandSamplingID":1066,"SandSamplingNumID":1041,"FileName":"Screenshot_2017-06-05-14-55-57-935_com.kc.shiptransport.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/1e6a70a0-3745-4075-b5f3-86a909043033.png"},{"ItemID":1072,"SandSamplingID":1066,"SandSamplingNumID":1041,"FileName":"1497861364341.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/0c34dce7-92fc-42ed-b02d-407b7d9d0dde.png"},{"ItemID":1073,"SandSamplingID":1066,"SandSamplingNumID":1041,"FileName":"Screenshot_2017-06-05-14-55-57-935_com.kc.shiptransport.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/1e6a70a0-3745-4075-b5f3-86a909043033.png"},{"ItemID":1066,"SandSamplingID":1066,"SandSamplingNumID":1041,"FileName":"1497861364341.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/0c34dce7-92fc-42ed-b02d-407b7d9d0dde.png"},{"ItemID":1067,"SandSamplingID":1066,"SandSamplingNumID":1041,"FileName":"Screenshot_2017-06-05-14-55-57-935_com.kc.shiptransport.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/1e6a70a0-3745-4075-b5f3-86a909043033.png"}],"ConstructionBoatAccount":"gy1","ConstructionBoatAccountName":"冠亚1"},{"ItemID":1042,"SandSamplingID":1066,"SamplingNum":"GGC","SandSamplingAttachmentRecordList":[{"ItemID":1074,"SandSamplingID":1066,"SandSamplingNumID":1042,"FileName":"1498011307944.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/636e33c5-542e-492d-ad0c-e7cfb01738d8.png"},{"ItemID":1075,"SandSamplingID":1066,"SandSamplingNumID":1042,"FileName":"1498011307944.png","FilePath":"https://cchk3.kingwi.org/Files/20170704/9d57db07-164b-4988-8db8-9321433e2113.png"}],"ConstructionBoatAccount":"jx6","ConstructionBoatAccountName":"吉星6"}]
     */

    private int ItemID;
    private String Creator;
    private String SandSamplingDate;
    private int SubcontractorInterimApproachPlanID;
    private String ConstructionBoatAccount;
    private String ShipName;
    private String Batch;
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

    public List<SandSamplingNumRecordListBean> getSandSamplingNumRecordList() {
        return SandSamplingNumRecordList;
    }

    public void setSandSamplingNumRecordList(List<SandSamplingNumRecordListBean> SandSamplingNumRecordList) {
        this.SandSamplingNumRecordList = SandSamplingNumRecordList;
    }

    public static class SandSamplingNumRecordListBean {
        /**
         * ItemID : 1040
         * SandSamplingID : 1066
         * SamplingNum : GGA
         * SandSamplingAttachmentRecordList : [{"ItemID":1064,"SandSamplingID":1066,"SandSamplingNumID":1040,"FileName":"1498011307944.png","FilePath":"https://cchk3.kingwi.org/Files/20170703/310a0cd7-ce18-434c-b871-236ad3c9b419.png"},{"ItemID":1065,"SandSamplingID":1066,"SandSamplingNumID":1040,"FileName":"1497861364341.png","FilePath":"https://cchk3.kingwi.org/Files/20170703/639c6948-1b9c-4eb9-a48c-68e9548c3622.png"}]
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
             * ItemID : 1064
             * SandSamplingID : 1066
             * SandSamplingNumID : 1040
             * FileName : 1498011307944.png
             * FilePath : https://cchk3.kingwi.org/Files/20170703/310a0cd7-ce18-434c-b871-236ad3c9b419.png
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
}
