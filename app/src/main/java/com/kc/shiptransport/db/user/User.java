package com.kc.shiptransport.db.user;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/7/6  16:59
 * @desc 用户账号与中文名
 */

public class User extends DataSupport{
    private String UserID;
    private String UserName;
    private String isConstructionPictureAdmin;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getIsConstructionPictureAdmin() {
        return isConstructionPictureAdmin;
    }

    public void setIsConstructionPictureAdmin(String isConstructionPictureAdmin) {
        this.isConstructionPictureAdmin = isConstructionPictureAdmin;
    }
}
