package com.kc.shiptransport.data.bean;

/**
 * @author qiuyongheng
 * @time 2017/6/26  14:13
 * @desc ${TODD}
 */

public class SampleCommitResult {

    /**
     * message : 1
     * ItemGuid : d61fde61-5007-498a-8568-152d2b434502
     * FilePath : http://cchk3.kingwi.org/Files/20170623/3c5cae81-18d3-4d0c-bae9-a0fb3cb2d34e.png
     * FileName : abc
     *  SuffixName  : jpg
     */

    private int message;
    private String ItemGuid;
    private String FilePath;
    private String FileName;
    private String SuffixName;

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public String getItemGuid() {
        return ItemGuid;
    }

    public void setItemGuid(String ItemGuid) {
        this.ItemGuid = ItemGuid;
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
