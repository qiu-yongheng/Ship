package com.kc.shiptransport.data.bean;

/**
 * @author qiuyongheng
 * @time 2017/5/16  16:39
 * @desc 登录结果
 */

public class LoginResult {

    /**
     * message : 1
     * UserID : yflf
     * UserName : 誉丰联发
     */

    private int message;
    private String UserID;
    private String UserName;

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
}
