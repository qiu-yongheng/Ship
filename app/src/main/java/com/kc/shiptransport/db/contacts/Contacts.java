package com.kc.shiptransport.db.contacts;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/8/11  9:30
 * @desc 通讯录
 */

public class Contacts extends DataSupport{

    /**
     * LoginName : kl
     * DisplayName : 卡乐
     * Department : 工程部
     * Email :
     * Title :
     * Mobile :
     * TelephoneNumber :
     * Sex :
     */

    private String LoginName;
    private String DisplayName;
    private String Department;
    private String Email;
    private String Title;
    private String Mobile;
    private String TelephoneNumber;
    private String Sex;

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String LoginName) {
        this.LoginName = LoginName;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String DisplayName) {
        this.DisplayName = DisplayName;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getTelephoneNumber() {
        return TelephoneNumber;
    }

    public void setTelephoneNumber(String TelephoneNumber) {
        this.TelephoneNumber = TelephoneNumber;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }
}
