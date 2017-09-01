package com.kc.shiptransport.mvp.supply;

import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/31  17:04
 * @desc ${TODD}
 */

public interface SupplyContract {
    interface View extends BaseView<Presenter> {
        // 显示验沙人
        void showSupplyMan(String supplyMan);
        // 当前日期
        void showCurrentDate(String date);
        // 待验沙船数
        void showStaySupplyShip(String num);
        // 显示选中周的任务计划
        void showWeekTask(List<String> dates, List<WeekTask> weekLists);
        // 每天任务量统计
        void showDayCount(Integer[] integers);
        // 是否显示加载
        void showLoading(boolean active);
        // 显示失败
        void showError();
        // 初始化spinner
        void showSpinner(List<SubcontractorList> value);
    }

    interface Presenter extends BasePresenter {
        // 验沙人名字
        void getSupplyManName();
        // 当前日期
        void getCurrentDate(int jumpWeek);
        // 待验沙船数
        void getStaySupplyShip();
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
