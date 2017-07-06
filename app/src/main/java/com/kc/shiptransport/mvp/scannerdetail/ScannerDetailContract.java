package com.kc.shiptransport.mvp.scannerdetail;

import com.kc.shiptransport.data.bean.ScannerListBean;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:57
 * @desc ${TODD}
 */

public interface ScannerDetailContract {
    interface View extends BaseView<Presenter> {
        void showTitle(WeekTask title);
        void showLoading(boolean isShow);
        void showError(String msg);
        void showDatas(List<ScannerListBean> scannerImage);
    }

    interface Presenter extends BasePresenter {
        void getTitle(int position);
        // 获取扫描件类型
        void getScannerType(int position, int type);
        void start(int position, int type);
    }
}
