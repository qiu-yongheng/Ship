package com.kc.shiptransport.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kc.shiptransport.adapter.ExpandableItemAdapter;

/**
 * 联系人
 */
public class Person implements MultiItemEntity {
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

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getTelephoneNumber() {
        return TelephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        TelephoneNumber = telephoneNumber;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_PERSON;
    }
}