package com.kc.shiptransport.data.bean.img;

/**
 * @author 邱永恒
 * @time 2017/11/27  8:53
 * @desc 图片提交返回结果
 */

public class ImgCommitResultBean {
    /**
     * message : 1
     * FilePath : http://cchk3.kingwi.org/Files/20170623/3c5cae81-18d3-4d0c-bae9-a0fb3cb2d34e.png
     * FileName : abc
     *  SuffixName  : jpg
     */
    private int message;
    private String FilePath;
    private String FileName;
    private String SuffixName;

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getSuffixName() {
        return SuffixName;
    }

    public void setSuffixName(String suffixName) {
        SuffixName = suffixName;
    }
}
