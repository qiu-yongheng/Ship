package com.kc.shiptransport.data.bean.img;

/**
 * @author 邱永恒
 * @time 2017/11/27  8:52
 * @desc 封装图片提交数据
 */

public class ImgCommitBean {
    private String ByteDataStr;
    private String SuffixName;
    private String FileName;

    public ImgCommitBean(String byteDataStr, String suffixName, String fileName) {
        ByteDataStr = byteDataStr;
        SuffixName = suffixName;
        FileName = fileName;
    }

    public String getByteDataStr() {
        return ByteDataStr;
    }

    public void setByteDataStr(String byteDataStr) {
        ByteDataStr = byteDataStr;
    }

    public String getSuffixName() {
        return SuffixName;
    }

    public void setSuffixName(String suffixName) {
        SuffixName = suffixName;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    @Override
    public String toString() {
        return "ImgCommitBean{" +
                ", SuffixName='" + SuffixName + '\'' +
                ", FileName='" + FileName + '\'' +
                '}';
    }
}
