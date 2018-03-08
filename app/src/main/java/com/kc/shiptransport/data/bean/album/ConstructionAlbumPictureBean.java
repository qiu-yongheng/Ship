package com.kc.shiptransport.data.bean.album;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2018/3/8  17:47
 * @desc 相册图片列表
 */

public class ConstructionAlbumPictureBean {

    /**
     * AllRowCount : 6
     * AllPageCount : 1
     * Data : [{"rownumber":1,"ItemID":6,"AlbumID":1,"AlbumName":"相册1","Summary":"摘要6","FileName":"6.jpg","FilePath":"http://www.cchk3.com/Files/20171113/b143b86a-f4b9-40d8-8cfe-e5542a4fb3d9..jpeg"},{"rownumber":2,"ItemID":5,"AlbumID":1,"AlbumName":"相册1","Summary":"摘要5","FileName":"5.jpg","FilePath":"http://www.cchk3.com/Files/20171113/a4da6c1b-c793-4b6a-af7f-8f2e6c7d3ba3..jpeg"},{"rownumber":3,"ItemID":4,"AlbumID":1,"AlbumName":"相册1","Summary":"摘要4","FileName":"4.jpg","FilePath":"http://www.cchk3.com/Files/20171113/0c80ac47-d15f-4812-9bd8-cfb70383bcb8..jpeg"},{"rownumber":4,"ItemID":3,"AlbumID":1,"AlbumName":"相册1","Summary":"摘要3","FileName":"3.jpg","FilePath":"http://www.cchk3.com/Files/20171113/aedabf15-aa98-4b1f-aedb-2cb78a66011f..jpeg"},{"rownumber":5,"ItemID":2,"AlbumID":1,"AlbumName":"相册1","Summary":"摘要2","FileName":"2.jpg","FilePath":"http://www.cchk3.com/Files/20171106/151b9630-8f05-47e6-99a4-e17c15569297..jpeg"},{"rownumber":6,"ItemID":1,"AlbumID":1,"AlbumName":"相册1","Summary":"摘要1","FileName":"1.jpg","FilePath":"http://www.cchk3.com/Files/20171103/3ddd1373-fe69-455b-af19-68a327f642f1..jpeg"}]
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
         * ItemID : 6
         * AlbumID : 1
         * AlbumName : 相册1
         * Summary : 摘要6
         * FileName : 6.jpg
         * FilePath : http://www.cchk3.com/Files/20171113/b143b86a-f4b9-40d8-8cfe-e5542a4fb3d9..jpeg
         */

        private int rownumber;
        private int ItemID;
        private int AlbumID;
        private String AlbumName;
        private String Summary;
        private String FileName;
        private String FilePath;

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

        public int getAlbumID() {
            return AlbumID;
        }

        public void setAlbumID(int AlbumID) {
            this.AlbumID = AlbumID;
        }

        public String getAlbumName() {
            return AlbumName;
        }

        public void setAlbumName(String AlbumName) {
            this.AlbumName = AlbumName;
        }

        public String getSummary() {
            return Summary;
        }

        public void setSummary(String Summary) {
            this.Summary = Summary;
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
