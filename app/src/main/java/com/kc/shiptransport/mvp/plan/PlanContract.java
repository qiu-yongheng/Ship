package com.kc.shiptransport.mvp.plan;

import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/16  19:49
 * @desc ${TODD}
 */

public interface PlanContract {
    interface View extends BaseView<Presenter> {
        void showTitle(String title);
        // 当前日期
        void showCurrentDate(String date);
        // 任务量
        void showTaskVolume();
        // 任务要求
        void showTaskRequire();
        // 当前任务总量
        void showTotalTaskVolume(int total);
        // 本周任务分配
        void showWeekTask(List<String> dates, List<WeekTask> weekLists);
        // 每天任务量统计
        void showDayCount(Integer[] integers);
        void showLoading(boolean active);
        void showSuccess();
    }

    interface Presenter extends BasePresenter {
        void getTitle(int jumpWeek);
        // 当前日期
        void getCurrentDate(int jumpWeek);
        // 任务量
        void getTaskVolume();
        // 任务要求
        void getTaskRequire();
        // 当前任务总量
        void getTotalTaskVolume(Integer[] integers);
        // 本周任务分配
        void getWeekTask(int jumpWeek);
        // 每天任务量统计
        void getDayCount();
        // 提交数据
        void doCommit();
        // 刷新数据
        void doRefresh(String SubcontractorAccount, String StartDay, String EndDay, int jumpWeek);
        //
        void start(int jumpWeek);
    }
}
