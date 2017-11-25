package com.kc.shiptransport.data.bean.hse;

/**
 * @author 邱永恒
 * @time 2017/11/23  10:21
 * @desc ${TODD}
 */

public class HseCheckSelectBean {
    private String startDate;
    private String endDate;
    private String CheckedShipAccount;
    private String Creator;

    public HseCheckSelectBean(String startDate, String endDate, String checkedShipAccount, String creator) {
        this.startDate = startDate;
        this.endDate = endDate;
        CheckedShipAccount = checkedShipAccount;
        Creator = creator;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCheckedShipAccount() {
        return CheckedShipAccount;
    }

    public void setCheckedShipAccount(String checkedShipAccount) {
        CheckedShipAccount = checkedShipAccount;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }
}
