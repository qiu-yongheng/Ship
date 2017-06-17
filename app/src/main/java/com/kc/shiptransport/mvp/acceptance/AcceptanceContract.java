package com.kc.shiptransport.mvp.acceptance;

import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/1  15:10
 * @desc ${TODD}
 */

public interface AcceptanceContract {
    interface View extends BaseView<Presenter> {
        // 显示验收人
        void showAcceptanceMan(String acceptanceMan);
        // 当前日期
        void showCurrentDate(String date);
        // 待验收船数
        void showStayAcceptanceShip(String num);
        // 显示选中周的任务计划
        void showWeekTask(List<String> dates, List<WeekTask> weekLists);
        // 每天任务量统计
        void showDayCount(Double[] integers);
        // 是否显示加载
        void showLoading(boolean active);
        // 显示失败
        void showError();
        // 初始化spinner
        void showSpinner(List<SubcontractorList> value);
    }

    interface Presenter extends BasePresenter {
        // 验收人名字
        void getAcceptanceManName();
        // 当前日期
        void getCurrentDate(int jumpWeek);
        // 待验收船数
        void getStayAcceptanceShip();
        // 本周任务分配
        void getWeekTask(int jumpWeek);
        // 每天任务量统计
        void getDayCount();
        // 刷新数据
        void doRefresh(int jumpWeek, String subcontractorAccount);
        // 加载数据
        void start(int jumpWeek, String subcontractorAccount);
        // 获取所有分包商
        void getSubcontractor();
    }
}
