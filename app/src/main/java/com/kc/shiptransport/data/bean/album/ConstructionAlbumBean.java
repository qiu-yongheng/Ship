package com.kc.shiptransport.data.bean.album;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2018/3/8  12:48
 * @desc 相册列表
 */

public class ConstructionAlbumBean {

    /**
     * AllRowCount : 3
     * AllPageCount : 1
     * Data : [{"rownumber":1,"ItemID":3,"AlbumName":"相册3","Remark":"描述3","TotalNumber":"0","Attachment":""},{"rownumber":2,"ItemID":2,"AlbumName":"相册2","Remark":"描述2","TotalNumber":"2","Attachment":"http://www.cchk3.com/Files/20180111/252e967c-b765-49ca-9170-8a6a8d80e6cf..jpeg,http://www.cchk3.com/Files/20171114/25ece53d-eca4-452b-9739-d5d640801f7e..jpeg,"},{"rownumber":3,"ItemID":1,"AlbumName":"相册1","Remark":"描述1","TotalNumber":"6","Attachment":"http://www.cchk3.com/Files/20171113/b143b86a-f4b9-40d8-8cfe-e5542a4fb3d9..jpeg,http://www.cchk3.com/Files/20171113/a4da6c1b-c793-4b6a-af7f-8f2e6c7d3ba3..jpeg,http://www.cchk3.com/Files/20171113/0c80ac47-d15f-4812-9bd8-cfb70383bcb8..jpeg,http://www.cchk3.com/Files/20171113/aedabf15-aa98-4b1f-aedb-2cb78a66011f..jpeg,"}]
     */

    private int AllRowCount;
    private int AllPageCount;
    private List<DataBean> Data;

    public int getAllRowCount() {
        return AllRowCount;
    }

    public void setAllRowCount(int AllRowCount) {
        this.AllRowCount = AllRowCount;
    }

    public int getAllPageCount() {
        return AllPageCount;
    }

    public void setAllPageCount(int AllPageCount) {
        this.AllPageCount = AllPageCount;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * rownumber : 1
         * ItemID : 3
         * AlbumName : 相册3
         * Remark : 描述3
         * TotalNumber : 0
         * Attachment :
         */

        private int rownumber;
        private int ItemID;
        private String AlbumName;
        private String Remark;
        private String TotalNumber;
        private String Attachment;

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

        public String getAlbumName() {
            return AlbumName;
        }

        public void setAlbumName(String AlbumName) {
            this.AlbumName = AlbumName;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public String getTotalNumber() {
            return TotalNumber;
        }

        public void setTotalNumber(String TotalNumber) {
            this.TotalNumber = TotalNumber;
        }

        public String getAttachment() {
            return Attachment;
        }

        public void setAttachment(String Attachment) {
            this.Attachment = Attachment;
        }
    }
}
