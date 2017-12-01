package com.kc.shiptransport.data.bean.hse.rectification;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/28  15:59
 * @desc HSE整改详细数据
 */

public class HseRectificationBean {

    /**
     * ItemID : 9
     * DefectRecordID : 1014
     * HSECheckedRecordID : 1016
     * RectificationTime : 2017-11-29 11:07:45
     * Remark :
     * CreatorName : 测试用户
     * Creator : csyh
     * IsSubmitted : 1
     * RectificationAttachmentList : [{"ItemID":"13","RectificationRecordID":"9","FilePath":"https://cchk3.kingwi.org/Files/20171129/42f0c8d9-e151-48af-9902-dc57ad3b5cad.png","FileName":"Screenshot_2017-10-24-11-44-39-638_com.kc.shiptransport.official.png"}]
     * DefectAttachmentList : [{"ItemID":"1022","DefectRecordID":"1014","FilePath":"https://cchk3.kingwi.org/Files/20171129/28130b8d-f53a-49ec-b35c-789e7f02cc1f.jpg","FileName":"qq_pic_merged_1510910408759.jpg"},{"ItemID":"1021","DefectRecordID":"1014","FilePath":"https://cchk3.kingwi.org/Files/20171129/e29c263d-086e-4a38-a531-98c62f2b2691.jpg","FileName":"IMG_20171026_234754.jpg"}]
     */
    private int ItemID;
    private int DefectRecordID;
    private int HSECheckedRecordID;
    private String RectificationTime;
    private String Remark;
    private String CreatorName;
    private String Creator;
    private int IsSubmitted;
    private List<RectificationAttachmentListBean> RectificationAttachmentList;
    private List<DefectAttachmentListBean> DefectAttachmentList;

    public HseRectificationBean(int itemID, int defectRecordID, int HSECheckedRecordID, String rectificationTime, String remark, String creatorName, String creator, int isSubmitted, List<RectificationAttachmentListBean> rectificationAttachmentList, List<DefectAttachmentListBean> defectAttachmentList) {
        ItemID = itemID;
        DefectRecordID = defectRecordID;
        this.HSECheckedRecordID = HSECheckedRecordID;
        RectificationTime = rectificationTime;
        Remark = remark;
        CreatorName = creatorName;
        Creator = creator;
        IsSubmitted = isSubmitted;
        RectificationAttachmentList = rectificationAttachmentList;
        DefectAttachmentList = defectAttachmentList;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public int getDefectRecordID() {
        return DefectRecordID;
    }

    public void setDefectRecordID(int DefectRecordID) {
        this.DefectRecordID = DefectRecordID;
    }

    public int getHSECheckedRecordID() {
        return HSECheckedRecordID;
    }

    public void setHSECheckedRecordID(int HSECheckedRecordID) {
        this.HSECheckedRecordID = HSECheckedRecordID;
    }

    public String getRectificationTime() {
        return RectificationTime;
    }

    public void setRectificationTime(String RectificationTime) {
        this.RectificationTime = RectificationTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getCreatorName() {
        return CreatorName;
    }

    public void setCreatorName(String CreatorName) {
        this.CreatorName = CreatorName;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public int getIsSubmitted() {
        return IsSubmitted;
    }

    public void setIsSubmitted(int IsSubmitted) {
        this.IsSubmitted = IsSubmitted;
    }

    public List<RectificationAttachmentListBean> getRectificationAttachmentList() {
        return RectificationAttachmentList;
    }

    public void setRectificationAttachmentList(List<RectificationAttachmentListBean> RectificationAttachmentList) {
        this.RectificationAttachmentList = RectificationAttachmentList;
    }

    public List<DefectAttachmentListBean> getDefectAttachmentList() {
        return DefectAttachmentList;
    }

    public void setDefectAttachmentList(List<DefectAttachmentListBean> DefectAttachmentList) {
        this.DefectAttachmentList = DefectAttachmentList;
    }

    public static class RectificationAttachmentListBean {
        /**
         * ItemID : 13
         * RectificationRecordID : 9
         * FilePath : https://cchk3.kingwi.org/Files/20171129/42f0c8d9-e151-48af-9902-dc57ad3b5cad.png
         * FileName : Screenshot_2017-10-24-11-44-39-638_com.kc.shiptransport.official.png
         */

        private int ItemID;
        private int RectificationRecordID;
        private String FilePath;
        private String FileName;

        public int getItemID() {
            return ItemID;
        }

        public void setItemID(int ItemID) {
            this.ItemID = ItemID;
        }

        public int getRectificationRecordID() {
            return RectificationRecordID;
        }

        public void setRectificationRecordID(int RectificationRecordID) {
            this.RectificationRecordID = RectificationRecordID;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }

        public String getFileName() {
            return FileName;
        }

        public void setFileName(String FileName) {
            this.FileName = FileName;
        }
    }

    public static class DefectAttachmentListBean {
        /**
         * ItemID : 1022
         * DefectRecordID : 1014
         * FilePath : https://cchk3.kingwi.org/Files/20171129/28130b8d-f53a-49ec-b35c-789e7f02cc1f.jpg
         * FileName : qq_pic_merged_1510910408759.jpg
         */

        private int ItemID;
        private int DefectRecordID;
        private String FilePath;
        private String FileName;

        public int getItemID() {
            return ItemID;
        }

        public void setItemID(int ItemID) {
            this.ItemID = ItemID;
        }

        public int getDefectRecordID() {
            return DefectRecordID;
        }

        public void setDefectRecordID(int DefectRecordID) {
            this.DefectRecordID = DefectRecordID;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }

        public String getFileName() {
            return FileName;
        }

        public void setFileName(String FileName) {
            this.FileName = FileName;
        }
    }
}
