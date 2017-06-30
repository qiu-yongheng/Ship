package com.kc.shiptransport.mvp.basemvp;

import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/13  15:38
 * @desc ${TODD}
 */

public interface BaseMvpContract {
    interface View extends BaseView<Presenter> {
        // 显示标题(判断当前是什么模块)
        void showTitle(String data);
        // 可以显示操作人等信息
        void showTitleOtherInfo(String data);
        // 显示时间
        void showTime(String data);
        // 显示待验收的信息
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
        // 显示标题(判断当前是什么模块)
        void getTitle();
        // 可以显示操作人等信息
        void getTitleOtherInfo();
        // 获取时间
        void getTime(int jumpWeek);
        // 获取待验收数据
        void getStayInfo(int type);
        // 本周任务分配
        void getWeekTask(int jumpWeek);
        // 每天任务量统计
        void getDayCount();
        // 刷新数据
        void doRefresh(int jumpWeek, int type, String account);
        // 获取数据
        void start(int jumpWeek, int type, String account);
        // 获取所有分包商
        void getSubcontractorList();
    }
}
