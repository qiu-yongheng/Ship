package com.kc.shiptransport.mvp.plansetting;

import com.kc.shiptransport.db.ship.Ship;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/17  17:12
 * @desc ${TODD}
 */

public interface PlanSetContract {
    interface View extends BaseView<Presenter> {
        // 显示船舶类型
        void showShipCategory(List<Ship> value, String list, Boolean isAllow);
        // 显示船舶数量
        void showShipcount(int num);
        // 显示计划量
        void showPlanMeasure(double measure);
    }
    interface Presenter extends BasePresenter {
        void getShipCategory(String s);
        void getShipCount(String date);
        void getPlanMeasure(String date);
    }
}
