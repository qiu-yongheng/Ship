package com.kc.shiptransport.mvp.exitapplicationdetail;

import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author qiuyongheng
 * @time 2017/7/12  10:00
 * @desc ${TODD}
 */

public interface ExitApplicationDetailContract {
    interface View extends BaseView<Presenter> {
        void showCommitResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {
        void getDates();
        void commit();
    }
}
