package com.kc.shiptransport.mvp.plansetting;

import com.kc.shiptransport.db.Ship;
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
        void showShipCategory(List<List<Ship>> value, String list);
    }
    interface Presenter extends BasePresenter {
        void getShipCategory(String s);
    }
}
