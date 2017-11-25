package com.kc.shiptransport.mvp.todayplan;

import com.kc.shiptransport.data.bean.todayplan.TodayPlanBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author 邱永恒
 * @time 2017/11/2  15:24
 * @desc ${TODD}
 */

public interface TodayPlanContract {
    interface View extends BaseView<Presenter> {
        void showTodayPlan(TodayPlanBean bean);
    }

    interface Presenter extends BasePresenter {
        void getTodayPlan(String date);
    }
}
