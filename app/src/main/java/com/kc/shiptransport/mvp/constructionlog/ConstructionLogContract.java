package com.kc.shiptransport.mvp.constructionlog;

import com.kc.shiptransport.db.pump.PumpShip;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/6  15:44
 * @desc ${TODD}
 */

public interface ConstructionLogContract {
    interface View extends BaseView<Presenter> {
        void showPumpShip(List<PumpShip> list);
    }

    interface Presenter extends BasePresenter {
        void getPumpShip();
    }
}
