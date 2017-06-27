package com.kc.shiptransport.mvp.recordedsanddetail;

import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/19  11:29
 * @desc ${TODD}
 */

public interface RecordedSandDetailContract {
    interface View extends BaseView<Presenter> {
        void showSpinner(List<String> list);
        void showShip();
    }

    interface Presenter extends BasePresenter {
        void getSpinner();
        void getShip(int position);
    }
}
