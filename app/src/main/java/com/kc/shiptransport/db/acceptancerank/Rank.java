package com.kc.shiptransport.db.acceptancerank;

import org.litepal.crud.DataSupport;

/**
 * @author 邱永恒
 * @time 2017/8/10  10:48
 * @desc 供应商评分排行
 */

public class Rank extends DataSupport{

    /**
     * SubcontractorAccount : csfbs
     * SubcontractorName : 测试供应商
     * TotalMaterialIntegrity : 20
     * TotalMaterialTimeliness : 28
     * TotalScore : 48
     * TotalCount : 6
     * AvgTotalScore : 4
     * AvgTotalMaterialIntegrity : 3
     * AvgTotalMaterialTimeliness : 4
     */

    private String SubcontractorAccount;
    private String SubcontractorName;
    private String TotalMaterialIntegrity;
    private String TotalMaterialTimeliness;
    private String TotalScore;
    private int TotalCount;
    private String AvgTotalScore;
    private String AvgTotalMaterialIntegrity;
    private String AvgTotalMaterialTimeliness;

    public String getSubcontractorAccount() {
        return SubcontractorAccount;
    }

    public void setSubcontractorAccount(String SubcontractorAccount) {
        this.SubcontractorAccount = SubcontractorAccount;
    }

    public String getSubcontractorName() {
        return SubcontractorName;
    }

    public void setSubcontractorName(String SubcontractorName) {
        this.SubcontractorName = SubcontractorName;
    }

    public String getTotalMaterialIntegrity() {
        return TotalMaterialIntegrity;
    }

    public void setTotalMaterialIntegrity(String TotalMaterialIntegrity) {
        this.TotalMaterialIntegrity = TotalMaterialIntegrity;
    }

    public String getTotalMaterialTimeliness() {
        return TotalMaterialTimeliness;
    }

    public void setTotalMaterialTimeliness(String TotalMaterialTimeliness) {
        this.TotalMaterialTimeliness = TotalMaterialTimeliness;
    }

    public String getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(String TotalScore) {
        this.TotalScore = TotalScore;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public String getAvgTotalScore() {
        return AvgTotalScore;
    }

    public void setAvgTotalScore(String AvgTotalScore) {
        this.AvgTotalScore = AvgTotalScore;
    }

    public String getAvgTotalMaterialIntegrity() {
        return AvgTotalMaterialIntegrity;
    }

    public void setAvgTotalMaterialIntegrity(String AvgTotalMaterialIntegrity) {
        this.AvgTotalMaterialIntegrity = AvgTotalMaterialIntegrity;
    }

    public String getAvgTotalMaterialTimeliness() {
        return AvgTotalMaterialTimeliness;
    }

    public void setAvgTotalMaterialTimeliness(String AvgTotalMaterialTimeliness) {
        this.AvgTotalMaterialTimeliness = AvgTotalMaterialTimeliness;
    }
}
