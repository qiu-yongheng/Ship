package com.kc.shiptransport.data.bean.boatinquire;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/29  10:53
 * @desc 砂船自查
 */

public class BoatInquireBean {
    /**
     * rownumber : 1
     * ItemID : 1
     * ShipAccount : csgscb
     * ShipName : 测试供砂船舶
     * CheckDate : 2017-11-23 00:00:00
     * CreatorName : 测试用户
     * Creator : csyh
     * Remark : abc
     * List : [{"rownumber":"1","SelfCheckRecordID":1,"SelfCheckItemID":"1","SelfCheckItemName":"船员已按要求船舶安全帽，救生衣及安全鞋","CheckedResult":"1"},{"rownumber":"2","SelfCheckRecordID":1,"SelfCheckItemID":"2","SelfCheckItemName":"皮带桥梁已四面围封，砂料不会从桥梁中漏出","CheckedResult":"-1"}]
     */

    private int rownumber;
    private int ItemID;
    private String ShipAccount;
    private String ShipName;
    private String CheckDate;
    private String CreatorName;
    private String Creator;
    private String Remark;
    private int IsAllowUpdate;
    private java.util.List<ListBean> List;

    public BoatInquireBean(int rownumber, int itemID, String shipAccount, String shipName, String checkDate, String creatorName, String creator, String remark, java.util.List<ListBean> list) {
        this.rownumber = rownumber;
        ItemID = itemID;
        ShipAccount = shipAccount;
        ShipName = shipName;
        CheckDate = checkDate;
        CreatorName = creatorName;
        Creator = creator;
        Remark = remark;
        List = list;
    }

    public BoatInquireBean(int rownumber, int itemID, String shipAccount, String shipName, String checkDate, String creatorName, String creator, String remark, int isAllowUpdate, java.util.List<ListBean> list) {
        this.rownumber = rownumber;
        ItemID = itemID;
        ShipAccount = shipAccount;
        ShipName = shipName;
        CheckDate = checkDate;
        CreatorName = creatorName;
        Creator = creator;
        Remark = remark;
        IsAllowUpdate = isAllowUpdate;
        List = list;
    }

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

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String CheckDate) {
        this.CheckDate = CheckDate;
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

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public int getIsAllowUpdate() {
        return IsAllowUpdate;
    }

    public void setIsAllowUpdate(int isAllowUpdate) {
        IsAllowUpdate = isAllowUpdate;
    }

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }

    public static class ListBean {
        /**
         * rownumber : 1
         * SelfCheckRecordID : 1
         * SelfCheckItemID : 1
         * SelfCheckItemName : 船员已按要求船舶安全帽，救生衣及安全鞋
         * CheckedResult : 1
         */

        private int rownumber;
        private int SelfCheckRecordID;
        private int SelfCheckItemID;
        private String SelfCheckItemName;
        private int CheckedResult;

        public int getRownumber() {
            return rownumber;
        }

        public void setRownumber(int rownumber) {
            this.rownumber = rownumber;
        }

        public int getSelfCheckRecordID() {
            return SelfCheckRecordID;
        }

        public void setSelfCheckRecordID(int SelfCheckRecordID) {
            this.SelfCheckRecordID = SelfCheckRecordID;
        }

        public int getSelfCheckItemID() {
            return SelfCheckItemID;
        }

        public void setSelfCheckItemID(int SelfCheckItemID) {
            this.SelfCheckItemID = SelfCheckItemID;
        }

        public String getSelfCheckItemName() {
            return SelfCheckItemName;
        }

        public void setSelfCheckItemName(String SelfCheckItemName) {
            this.SelfCheckItemName = SelfCheckItemName;
        }

        public int getCheckedResult() {
            return CheckedResult;
        }

        public void setCheckedResult(int CheckedResult) {
            this.CheckedResult = CheckedResult;
        }
    }
}
