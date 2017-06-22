package com.kc.shiptransport.mvp.scannerdetail;

import com.kc.shiptransport.db.ScannerImage;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:57
 * @desc ${TODD}
 */

public interface ScannerDetailContract {
    interface View extends BaseView<Presenter> {
        void showTitle(String title);
        void showLoading(boolean isShow);
        void showError(String msg);
        void showDatas(ScannerImage scannerImage);
    }

    interface Presenter extends BasePresenter {
        void getTitle(int position);
        void getDetailForItemID(int position);
        void start(int position);
    }
}
