package com.kc.shiptransport.mvp.amount;

import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/13  15:38
 * @desc ${TODD}
 */

public interface AmountContract {
    interface View extends BaseView<Presenter> {
        void showTitle(String data);
        void showTitleOtherInfo(String data);
        void showTime(String data);
        void showStayInfo(String data);
        // 显示选中周的任务计划
        void showWeekTask(List<String> dates);
        // 每天任务量统计
        void showDayCount(Integer[] integers);
        // 是否显示加载
        void showLoading(boolean active);
        // 显示失败信息
        void showError(String msg);
        // 初始化spinner
        void showSpinner(List<SubcontractorList> value);
    }

    interface Presenter extends BasePresenter {
        void getTitle();
        void getTitleOtherInfo();
        void getTime(int jumpWeek);
        void getStayInfo();
        // 本周任务分配
        void getWeekTask(int jumpWeek);
        // 每天任务量统计
        void getDayCount();
        // 刷新数据
        void doRefresh(int jumpWeek, String subcontractorAccount);
        //
        void start(int jumpWeek, String subcontractorAccount);
        // 获取所有供应商
        void getSubcontractor();
    }
}
