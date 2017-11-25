package com.kc.shiptransport.db.contacts;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * @author 邱永恒
 * @time 2017/8/11  9:30
 * @desc 通讯录
 */

public class Contacts extends DataSupport implements Serializable{
    /**
     * rownumber : 1
     * ItemID : 194
     * CId : 42
     * PId : 3
     * EnglishName : Wang Zhengliang
     * LoginName : wangzhengliang
     * Email : wangzl@fhdigz.com
     * Duties : 副总工程师
     * Mobile : 6680-6273
     * TelephoneNumber :
     * Sex :
     * Department : 王x亮
     * DisplayName :
     */

    private int rownumber;
    private int ItemID;
    private String CId;
    private String PId;
    private String EnglishName;
    private String LoginName;
    private String Email;
    private String Duties;
    private String Mobile;
    private String TelephoneNumber;
    private String Sex;
    private String Department;
    private String DisplayName;

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

    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public String getPId() {
        return PId;
    }

    public void setPId(String PId) {
        this.PId = PId;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String EnglishName) {
        this.EnglishName = EnglishName;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String LoginName) {
        this.LoginName = LoginName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getDuties() {
        return Duties;
    }

    public void setDuties(String Duties) {
        this.Duties = Duties;
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

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String DisplayName) {
        this.DisplayName = DisplayName;
    }
}
