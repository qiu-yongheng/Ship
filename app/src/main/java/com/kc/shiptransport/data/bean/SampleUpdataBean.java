package com.kc.shiptransport.data.bean;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/29  19:37
 * @desc 提交图片要提交的json
 */

public class SampleUpdataBean {

    /**
     * ItemID : 53
     * SubcontractorInterimApproachPlanID : 415
     * ConstructionBoatAccount : yflf
     * Creator : yflf
     * SandSamplingDate : 2017-02-17 10:47
     * SandSamplingNumRecordList : [{"SandSamplingAttachmentRecordList":[{"ItemID":"1","SandSamplingID":"53","SandSamplingNumID":"1","FileName":"aa.jpg","FilePath":"/20170628/qwqewqe1.jpg","SuffixName":"0"},{"ItemID":"2","SandSamplingID":"53","SandSamplingNumID":"2","FileName":"aa.jpg","FilePath":"/20170629/qwqewqe2.jpg","SuffixName":"0"},{"ItemID":"3","SandSamplingID":"53","SandSamplingNumID":"3","FileName":"aa.jpg","FilePath":"/20170628/qwqewqe3.jpg","SuffixName":"0"}],"ItemID":"1","SandSamplingID":"xxxxxx","SamplingNum":"xx022222"}]
     */

    private String ItemID;
    private String SubcontractorInterimApproachPlanID;
    private String ConstructionBoatAccount;
    private String Creator;
    private String SandSamplingDate;
    private List<SandSamplingNumRecordListBean> SandSamplingNumRecordList;

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String ItemID) {
        this.ItemID = ItemID;
    }

    public String getSubcontractorInterimApproachPlanID() {
        return SubcontractorInterimApproachPlanID;
    }

    public void setSubcontractorInterimApproachPlanID(String SubcontractorInterimApproachPlanID) {
        this.SubcontractorInterimApproachPlanID = SubcontractorInterimApproachPlanID;
    }

    public String getConstructionBoatAccount() {
        return ConstructionBoatAccount;
    }

    public void setConstructionBoatAccount(String ConstructionBoatAccount) {
        this.ConstructionBoatAccount = ConstructionBoatAccount;
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

    public List<SandSamplingNumRecordListBean> getSandSamplingNumRecordList() {
        return SandSamplingNumRecordList;
    }

    public void setSandSamplingNumRecordList(List<SandSamplingNumRecordListBean> SandSamplingNumRecordList) {
        this.SandSamplingNumRecordList = SandSamplingNumRecordList;
    }

    public static class SandSamplingNumRecordListBean {
        /**
         * SandSamplingAttachmentRecordList : [{"ItemID":"1","SandSamplingID":"53","SandSamplingNumID":"1","FileName":"aa.jpg","FilePath":"/20170628/qwqewqe1.jpg","SuffixName":"0"},{"ItemID":"2","SandSamplingID":"53","SandSamplingNumID":"2","FileName":"aa.jpg","FilePath":"/20170629/qwqewqe2.jpg","SuffixName":"0"},{"ItemID":"3","SandSamplingID":"53","SandSamplingNumID":"3","FileName":"aa.jpg","FilePath":"/20170628/qwqewqe3.jpg","SuffixName":"0"}]
         * ItemID : 1
         * SandSamplingID : xxxxxx
         * SamplingNum : xx022222
         */

        private String ItemID;
        private String SandSamplingID;
        private String SamplingNum;
        private List<SandSamplingAttachmentRecordListBean> SandSamplingAttachmentRecordList;

        public String getItemID() {
            return ItemID;
        }

        public void setItemID(String ItemID) {
            this.ItemID = ItemID;
        }

        public String getSandSamplingID() {
            return SandSamplingID;
        }

        public void setSandSamplingID(String SandSamplingID) {
            this.SandSamplingID = SandSamplingID;
        }

        public String getSamplingNum() {
            return SamplingNum;
        }

        public void setSamplingNum(String SamplingNum) {
            this.SamplingNum = SamplingNum;
        }

        public List<SandSamplingAttachmentRecordListBean> getSandSamplingAttachmentRecordList() {
            return SandSamplingAttachmentRecordList;
        }

        public void setSandSamplingAttachmentRecordList(List<SandSamplingAttachmentRecordListBean> SandSamplingAttachmentRecordList) {
            this.SandSamplingAttachmentRecordList = SandSamplingAttachmentRecordList;
        }

        public static class SandSamplingAttachmentRecordListBean {
            /**
             * ItemID : 1
             * SandSamplingID : 53
             * SandSamplingNumID : 1
             * FileName : aa.jpg
             * FilePath : /20170628/qwqewqe1.jpg
             * SuffixName : 0
             */

            private String ItemID;
            private String SandSamplingID;
            private String SandSamplingNumID;
            private String FileName;
            private String FilePath;
            private String SuffixName;

            public String getItemID() {
                return ItemID;
            }

            public void setItemID(String ItemID) {
                this.ItemID = ItemID;
            }

            public String getSandSamplingID() {
                return SandSamplingID;
            }

            public void setSandSamplingID(String SandSamplingID) {
                this.SandSamplingID = SandSamplingID;
            }

            public String getSandSamplingNumID() {
                return SandSamplingNumID;
            }

            public void setSandSamplingNumID(String SandSamplingNumID) {
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

            public String getSuffixName() {
                return SuffixName;
            }

            public void setSuffixName(String SuffixName) {
                this.SuffixName = SuffixName;
            }
        }
    }
}
