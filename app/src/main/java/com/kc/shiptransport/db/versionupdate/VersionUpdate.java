package com.kc.shiptransport.db.versionupdate;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/8/10  13:59
 * @desc ${TODD}
 */

public class VersionUpdate extends DataSupport{

    /**
     * ItemID : 8
     * Version : V20170807
     * FileName : app1
     * Creator : admin
     * FilePath : http://xx.apk
     * SystemDate : 2017-08-07
     */

    private int ItemID;
    private String Version;
    private String FileName;
    private String Creator;
    private String FilePath;
    private String SystemDate;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String Creator) {
        this.Creator = Creator;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String FilePath) {
        this.FilePath = FilePath;
    }

    public String getSystemDate() {
        return SystemDate;
    }

    public void setSystemDate(String SystemDate) {
        this.SystemDate = SystemDate;
    }
}
