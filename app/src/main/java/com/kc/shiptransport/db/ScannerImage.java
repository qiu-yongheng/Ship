package com.kc.shiptransport.db;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/6/22  15:25
 * @desc 航次信息扫描件 ( itemID 做为唯一标识)
 */

public class ScannerImage extends DataSupport{
    // 进场ID
    private int ItemID;

    // 装舱现场照片
    private String stowage;
    // 舱单
    private String ship_bill;
    // 托运单
    private String consignment_bill;
    // 碎石粉装创记录表
    private String gravel;
    // 选择计划航线图
    private String strip_plot_select;
    // 计划航线图
    private String strip_plot;
    // 送货单
    private String delivery_bill;
    // 预验收质量记录表
    private String qc;
    // 海关放行通知照片
    private String customs;
    // 航线对比图
    private String contrast;
    // 海关单
    private String customs_bill;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public String getStowage() {
        return stowage;
    }

    public void setStowage(String stowage) {
        this.stowage = stowage;
    }

    public String getShip_bill() {
        return ship_bill;
    }

    public void setShip_bill(String ship_bill) {
        this.ship_bill = ship_bill;
    }

    public String getConsignment_bill() {
        return consignment_bill;
    }

    public void setConsignment_bill(String consignment_bill) {
        this.consignment_bill = consignment_bill;
    }

    public String getGravel() {
        return gravel;
    }

    public void setGravel(String gravel) {
        this.gravel = gravel;
    }

    public String getStrip_plot_select() {
        return strip_plot_select;
    }

    public void setStrip_plot_select(String strip_plot_select) {
        this.strip_plot_select = strip_plot_select;
    }

    public String getStrip_plot() {
        return strip_plot;
    }

    public void setStrip_plot(String strip_plot) {
        this.strip_plot = strip_plot;
    }

    public String getDelivery_bill() {
        return delivery_bill;
    }

    public void setDelivery_bill(String delivery_bill) {
        this.delivery_bill = delivery_bill;
    }

    public String getQc() {
        return qc;
    }

    public void setQc(String qc) {
        this.qc = qc;
    }

    public String getCustoms() {
        return customs;
    }

    public void setCustoms(String customs) {
        this.customs = customs;
    }

    public String getContrast() {
        return contrast;
    }

    public void setContrast(String contrast) {
        this.contrast = contrast;
    }

    public String getCustoms_bill() {
        return customs_bill;
    }

    public void setCustoms_bill(String customs_bill) {
        this.customs_bill = customs_bill;
    }
}